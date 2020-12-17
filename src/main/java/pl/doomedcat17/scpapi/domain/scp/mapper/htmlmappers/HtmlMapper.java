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
        } else {
            ContentNode<String> contentNode = new ContentNode<>(ContentNodeType.TEXT);
            for (Node node : textElement.childNodes()) {
                if (node instanceof Element) {
                    Element element = (Element) node;
                    if (element.tagName().equals("br")) {
                        addText(contentNode, "\n");
                    } else if (element.is("[style*=\"text-decoration: line-through;\"]")) {
                        if (contentNode.getContent() != null) contentNodes.add(contentNode);
                        ContentNode<String> deletedText = new ContentNode<>(ContentNodeType.TEXT_DELETED, element.text());
                        contentNodes.add(deletedText);
                        contentNode = new ContentNode<>(ContentNodeType.TEXT);
                    } else {
                        addText(contentNode, element.text());
                    }
                } else {
                    String text = node.toString();
                    if (!text.isBlank()) {
                        addText(contentNode, text);
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

    private void addText(ContentNode<String> contentNode, String text) {
        if (contentNode.getContent() == null) {
            contentNode.setContent(text);
        } else contentNode.setContent(contentNode.getContent() + text);
    }

    protected List<ContentNode<?>> extractContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        for (Node node : element.childNodes()) {
            try {
                if (node instanceof Element) {
                    Element childElement = (Element) node;
                    HtmlMapper htmlMapper = HtmlMapperFactory.getHtmlMapper(childElement);
                    Appendix appendix = htmlMapper.mapElement(childElement);
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

    protected boolean isNotSimpleElement(Element element) {
        if (!element.parent().id().equals(PAGE_CONTENT_ID)) return false;
        Element strongElement = element.selectFirst("strong");
        if (strongElement != null) {
            if (ScpPattern.containsValue(strongElement.text(), "eng")) {
                return true;
            } else {
                return (strongElement.text().length() >= MIN_HEADER_LENGTH && strongElement.text().length() <= MAX_HEADER_LENGTH);
            }
        } else return false;
    }

}

