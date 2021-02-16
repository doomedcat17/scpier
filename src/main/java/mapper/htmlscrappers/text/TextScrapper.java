package mapper.htmlscrappers.text;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import data.content_node.TextNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextScrapper {

    private StyleScrapper styleScrapper = new StyleScrapper();

    public List<TextNode> scrapText(Element textElement) {
        List<TextNode> textNodes = new ArrayList<>();
        Map<String, String> elementStyles = styleScrapper.scrapStyles(textElement);
        for (Node node : textElement.childNodes()) {
            if (node instanceof Element) {
                Element innerElement = (Element) node;
                if (innerElement.is("br")) {
                    if (!textNodes.isEmpty()) {
                        TextNode lastTextNode = textNodes.get(textNodes.size() - 1);
                        if (lastTextNode.getContent() == null) {
                            lastTextNode.setContent("\n");
                        } else lastTextNode.setContent(lastTextNode.getContent() + "\n");
                    }
                } else {
                    if (innerElement.text().isBlank()) continue;
                    Map<String, String> innerElementStyles = styleScrapper.scrapStyles(innerElement);
                    if (!elementStyles.isEmpty()) innerElementStyles.putAll(elementStyles);
                    if (innerElement.childrenSize() != 0) {
                        mapInnerTextElements(textNodes, innerElement, innerElementStyles);
                    } else {
                        String text = innerElement.wholeText();
                        TextNode textNode = new TextNode(text, innerElementStyles);
                        textNodes.add(textNode);
                    }

                }
            } else {
                String text = node.toString();
                if (!text.isBlank()) {
                    TextNode textNode = new TextNode(text, elementStyles);
                    textNodes.add(textNode);
                }
            }
        }
        return textNodes;
    }

    private void mapInnerTextElements(List<TextNode> textNodes, Element element, Map<String, String> parentStyles) {
        //TODO TESTS NEEDED
        List<TextNode> innerTextNodes = new ArrayList<>();
        for (Node innerNode : element.childNodes()) {
            if (innerNode instanceof Element) {
                Element innerElement = (Element)  innerNode;
                List<TextNode> nodes = scrapText(innerElement);
                innerTextNodes.addAll(nodes);
            } else {
                innerTextNodes.add(new TextNode(innerNode.toString()));
            }
        }
        innerTextNodes.forEach(n -> n.addStyles(parentStyles));
        textNodes.addAll(innerTextNodes);
    }
}
