package com.doomedcat17.scpier.scrapper.htmlscrappers.text;

import com.doomedcat17.scpier.data.content_node.HyperlinkNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import com.doomedcat17.scpier.data.content_node.TextNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextScrapper {

    public static List<TextNode> scrapText(Element textElement, String source) {
        List<TextNode> textNodes = new ArrayList<>();
        Map<String, String> elementStyles = StyleScrapper.scrapStyles(textElement);
        if (textElement.is("br")) {
            textNodes.add(new TextNode("\n"));
            return textNodes;
        }
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
                } else if (innerElement.is("a") && !innerElement.hasClass("footnoteref")
                        && innerElement.hasAttr("href")
                        && (!innerElement.attr("href").equals("#") && !innerElement.attr("href").contains("javascript"))) {
                    String href = innerElement.attr("href");
                    if (href.startsWith("/")) {
                        href = source.substring(0, source.lastIndexOf('/'))+href;
                    }
                    HyperlinkNode hyperlinkNode = new HyperlinkNode(innerElement.wholeText(), href);
                    Map<String, String> innerElementStyles = StyleScrapper.scrapStyles(innerElement);
                    if (!elementStyles.isEmpty()) innerElementStyles.putAll(elementStyles);
                    hyperlinkNode.setStyles(innerElementStyles);
                    textNodes.add(hyperlinkNode);
                } else {
                    if (innerElement.text().isBlank()) continue;
                    Map<String, String> innerElementStyles = StyleScrapper.scrapStyles(innerElement);
                    if (!elementStyles.isEmpty()) innerElementStyles.putAll(elementStyles);
                    if (innerElement.childrenSize() != 0) {
                        textNodes.addAll(mapInnerTextElements(innerElement, innerElementStyles, source));
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

    private static List<TextNode> mapInnerTextElements(Element element, Map<String, String> parentStyles, String source) {
        List<TextNode> innerTextNodes = new ArrayList<>();
        for (Node innerNode : element.childNodes()) {
            if (innerNode instanceof Element) {
                Element innerElement = (Element)  innerNode;
                List<TextNode> nodes = scrapText(innerElement, source);
                innerTextNodes.addAll(nodes);
            } else {
                String text = innerNode.toString();
                if (!text.isBlank()) {
                    innerTextNodes.add(new TextNode(innerNode.toString()));
                }
            }
        }
        innerTextNodes.forEach(n -> n.addStyles(parentStyles));
        return innerTextNodes;
    }

    //styles of textnodes are ignored
    public static String margeTextNodes(List<TextNode> textNodes) {
        StringBuilder stringBuilder = new StringBuilder();
        textNodes.forEach(textNode -> stringBuilder.append(textNode.getContent()));
        return stringBuilder.toString();
    }
}
