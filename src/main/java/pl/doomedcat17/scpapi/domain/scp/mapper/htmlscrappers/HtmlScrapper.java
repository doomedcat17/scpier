package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import pl.doomedcat17.scpapi.data.content_node.ContentNode;
import pl.doomedcat17.scpapi.data.content_node.ContentNodeType;
import pl.doomedcat17.scpapi.data.content_node.TextNode;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.text.StyleScrapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.text.TextScrapper;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class HtmlScrapper {

    protected final String PAGE_CONTENT_ID = "page-content";

    public abstract Appendix scrapElement(Element element);

    protected TextScrapper textScrapper = new TextScrapper();
    protected StyleScrapper styleScrapper = new StyleScrapper();

    protected TitleFinder titleFinder = new TitleFinder();

    protected List<ContentNode<?>> scrapElementContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        for (Node node : element.childNodes()) {
            try {
                if (node instanceof Element) {
                    Element childElement = (Element) node;
                    if (childElement.tagName().equals("strong") && !childElement.parent().id().equals(PAGE_CONTENT_ID)) {
                        //map inner strong elements to text
                        contentNodes.addAll(textScrapper.scrapText(childElement));
                    } else {
                        HtmlScrapper htmlScrapper = HtmlMapperFactory.getHtmlMapper(childElement);
                        Appendix appendix = htmlScrapper.scrapElement(childElement);
                        if (appendix.hasTitle()) {
                            ContentNode<List<TextNode>> headingTextNodes = new ContentNode<>(ContentNodeType.HEADING, new ArrayList<>());
                            headingTextNodes.getContent().add(new TextNode(appendix.getTitle()));
                            contentNodes.add(new ContentNode<>(ContentNodeType.HEADING, appendix.getTitle()));
                        }
                        contentNodes.addAll(appendix.getContents());
                    }
                } else {
                    String text = node.toString();
                    if (!text.isBlank()) {
                        contentNodes.add(new TextNode(text));
                    }
                }
            } catch (MapperNotFoundException e) {
            }
        }
        return contentNodes;
    }

    protected String capitalizeText(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

}

