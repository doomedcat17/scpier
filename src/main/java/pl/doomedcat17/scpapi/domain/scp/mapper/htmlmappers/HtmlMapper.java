package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class HtmlMapper {

    protected final String PAGE_CONTENT_ID = "page-content";

    private final int MIN_HEADER_LENGTH = 21;

    private final int MAX_HEADER_LENGTH = 69;

    public abstract Appendix mapElement(Element element);

    protected List<ContentNode<String>> scrapText(Element textElement) {
        List<ContentNode<String>> contentNodes = new ArrayList<>();
        if (textElement.is("[style*=\"text-decoration: line-through;\"]")) {
            contentNodes.add(new ContentNode<>(ContentNodeType.TEXT_DELETED, textElement.text()));
        } else if (textElement.is("sup.footnoteref")) {
            contentNodes.add(new ContentNode<>(ContentNodeType.TEXT, "[" + textElement.text() + "]"));
        } else {
            ContentNode<String> contentNode = new ContentNode<>(ContentNodeType.TEXT);
            for (Node node : textElement.childNodes()) {
                if (node instanceof Element) {
                    Element element = (Element) node;
                    if (element.tagName().equals("br")) {
                        appendTextToContentNode(contentNode, "\n");
                    } else if (element.is("[style*=\"text-decoration: line-through;\"]")) {
                        if (contentNode.getContent() != null) contentNodes.add(contentNode);
                        ContentNode<String> deletedText = new ContentNode<>(ContentNodeType.TEXT_DELETED, element.text());
                        contentNodes.add(deletedText);
                        contentNode = new ContentNode<>(ContentNodeType.TEXT);
                    } else if (element.is("sup.footnoteref")) {
                        appendTextToContentNode(contentNode, "[" + element.text() + "]");
                    } else {
                        appendTextToContentNode(contentNode, element.text());
                    }
                } else {
                    String text = node.toString();
                    if (!text.isBlank()) {
                        appendTextToContentNode(contentNode, text);
                    }
                }
            }
            if (contentNode.getContent() != null) contentNodes.add(contentNode);
            trimContent(contentNodes);
        }
        return contentNodes;
    }

    private void trimContent(List<ContentNode<String>> contentNodes) {
        contentNodes.forEach(contentBox -> contentBox.setContent(contentBox.getContent().trim()));
    }

    private void appendTextToContentNode(ContentNode<String> contentNode, String text) {
        if (contentNode.getContent() == null) {
            contentNode.setContent(text);
        } else contentNode.setContent(contentNode.getContent() + text);
    }

    protected List<ContentNode<?>> mapElementContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        for (Node node : element.childNodes()) {
            try {
                if (node instanceof Element) {
                    Element childElement = (Element) node;
                    HtmlMapper htmlMapper = HtmlMapperFactory.getHtmlMapper(childElement);
                    Appendix appendix = htmlMapper.mapElement(childElement);
                    if (appendix.hasTitle()) {
                        appendix.addContentNode(new ContentNode<>(ContentNodeType.HEADING, appendix.getTitle()));
                    }
                    contentNodes.addAll(appendix.getContents());
                } else {
                    String text = node.toString().trim();
                    if (!text.isBlank()) {
                        contentNodes.add(new ContentNode<>(ContentNodeType.TEXT, text));
                    }
                }
            } catch (MapperNotFoundException e) {
                log.info(e.getMessage());
            }
        }
        return contentNodes;
    }

    protected boolean isNotSimpleElement(Element element) { //last changed
        if (!element.parent().id().equals(PAGE_CONTENT_ID)) return false;
        if (!element.tagName().equals("strong")) {
            Node node = element.childNode(0); //some strong elements are inside text content of element
            if (node instanceof Element) element = (Element) node;
            else return false;
        }
            if (element.tagName().equals("strong")) {
                if (ScpPattern.containsValue(element.text(), "eng")) {
                    return true;
                } else {
                    return (element.text().length() >= MIN_HEADER_LENGTH && element.text().length() <= MAX_HEADER_LENGTH);
                }
            } else return false;
        }

    protected boolean isTittle(String title) {
        if (ScpPattern.containsValue(title, "eng")) {
            return true;
        } else {
            return (title.length() >= MIN_HEADER_LENGTH && title.length() <= MAX_HEADER_LENGTH);
        }
    }

}

