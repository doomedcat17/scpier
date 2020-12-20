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

    private final int TITLE_DEPTH = 2; //how deep inside divs/blockquotes look for title

    public abstract Appendix mapElement(Element element);

    protected TextScrapper textScrapper = new TextScrapper();

    protected List<ContentNode<?>> mapElementContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        for (Node node : element.childNodes()) {
            try {
                if (node instanceof Element) {
                    Element childElement = (Element) node;
                    if (childElement.tagName().equals("strong") && !childElement.parent().id().equals(PAGE_CONTENT_ID)) {
                        //map inner strong elements to text
                        contentNodes.addAll(textScrapper.scrapText(childElement));
                    }
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

    protected boolean isTittle(String title) {
        if (ScpPattern.containsValue(title, "eng")) {
            return true;
        } else {
            return (title.length() >= MIN_HEADER_LENGTH && title.length() <= MAX_HEADER_LENGTH);
        }
    }

    protected String lookForTittle(List<ContentNode<?>> contentNodes) {
        String title = "";
        if (contentNodes.size() >= TITLE_DEPTH) {
            for (int i = 0; i < contentNodes.size(); i++) {
                ContentNode<?> contentNode = contentNodes.get(i);
                if (i == TITLE_DEPTH) break;
                if (contentNode.getContentNodeType().equals(ContentNodeType.HEADING)) {
                    ContentNode<String> headingNode = (ContentNode<String>) contentNode;
                    if (isTittle(headingNode.getContent())) title = headingNode.getContent();
                }
            }
        }
        return title;
    }

    protected List<ContentNode<?>> margeTextNodes(List<ContentNode<?>> nodes) {
        //TODO test needed
        ContentNode<String> lastTextNode = null;
        List<ContentNode<?>> mappedNodes = new ArrayList<>();
        for (ContentNode node: nodes) {
            if (node.getContentNodeType().equals(ContentNodeType.TEXT)) {
                if (lastTextNode == null) {
                    lastTextNode = node;
                } else {
                    lastTextNode.setContent(lastTextNode.getContent()+node.getContent().toString());
                }
            } else {
                if (lastTextNode != null) {
                    mappedNodes.add(lastTextNode);
                    lastTextNode = null;
                }
                mappedNodes.add(node);
            }
        }
        if (lastTextNode != null) mappedNodes.add(lastTextNode);
        return mappedNodes;
    }

}

