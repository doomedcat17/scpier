package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.w3c.dom.Text;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;
import pl.doomedcat17.scpapi.data.StyleType;
import pl.doomedcat17.scpapi.data.TextNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextScrapperRefactor {

    private StyleScrapper styleScrapper = new StyleScrapper();

    public List<TextNode> scrapText(Element textElement) {
        List<TextNode> textNodes = new ArrayList<>();
        Map<String, String> parentStyles = styleScrapper.scrapStyles(textElement);
        for (Node node : textElement.childNodes()) {
            if (node instanceof Element) {
                Element element = (Element) node;
                if (element.is("br")) {
                    if (!textNodes.isEmpty()) {
                       TextNode lastTextNode = textNodes.get(textNodes.size() - 1);
                        if (lastTextNode.getContent() == null) {
                            lastTextNode.setContent("\n");
                        } else lastTextNode.setContent(lastTextNode.getContent() + "\n");
                    }
                } else {
                    String text = element.text();
                    Map<String, String> elementStyles =  styleScrapper.scrapStyles(element);
                    if (!parentStyles.isEmpty()) elementStyles.putAll(parentStyles);
                    TextNode textNode = new TextNode(text, elementStyles);
                    textNodes.add(textNode);
                }
            } else {
                String text = node.toString();
                if (!text.isBlank()) {
                    TextNode textNode = new TextNode(text);
                    textNodes.add(textNode);
                }
            }
        }
        return textNodes;
    }


}
