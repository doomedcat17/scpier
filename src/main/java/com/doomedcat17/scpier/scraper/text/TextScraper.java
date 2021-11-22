package com.doomedcat17.scpier.scraper.text;

import com.doomedcat17.scpier.data.content.HyperlinkNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextScraper {

    public static List<TextNode> scrapText(Element textElement, String sourceUrl) {
        try {
            List<TextNode> textNodes = new ArrayList<>();
            if (textElement.is("br")) {
                textNodes.add(new TextNode("\n"));
                return textNodes;
            } else if (isLink(textElement)) {
                HyperlinkNode hyperlinkNode = scrapLink(textElement, sourceUrl);
                textNodes.add(hyperlinkNode);
            } else textNodes = scrapNodes(textElement.childNodes(), sourceUrl);
            if (!textNodes.isEmpty()) {
                Map<String, String> cssProperties = StyleScraper.scrapStyles(textElement);
                textNodes.forEach(textNode -> applyParentStyling(textNode, cssProperties));
            }
            return textNodes;
        } catch (Exception e) {
            throw new ElementScraperException(e);
        }
    }

    private static List<TextNode> scrapNodes(List<Node> nodes, String sourceUrl) {
        List<TextNode> textNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node instanceof Element) {
                Element innerElement = (Element) node;
                if (innerElement.is("br")) {
                    appendNewLineChar(textNodes);
                } else if (isLink(innerElement)) {
                    textNodes.add(scrapLink(innerElement, sourceUrl));
                } else {
                    if (innerElement.text().isBlank()) continue;
                    if (innerElement.childrenSize() != 0) {
                        List<TextNode> innerTextNodes = scrapText(innerElement, sourceUrl);
                        textNodes.addAll(innerTextNodes);
                    } else {
                        Map<String, String> innerElementStyling = StyleScraper.scrapStyles(innerElement);
                        String text = innerElement.wholeText();
                        TextNode textNode = new TextNode(text, innerElementStyling);
                        textNodes.add(textNode);
                    }
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

    private static void applyParentStyling(TextNode childTextNode, Map<String, String> parentStyling) {
        if (!childTextNode.getContent().isBlank()) {
            parentStyling
                    .forEach((styleProperty, value) -> {
                        if (!childTextNode.getStyles().containsKey(styleProperty)) {
                            childTextNode.getStyles().put(styleProperty, value);
                        }
                    });
        }
    }

    private static void appendNewLineChar(List<TextNode> textNodes) {
        if (!textNodes.isEmpty()) {
            TextNode lastTextNode = textNodes.get(textNodes.size() - 1);
            if (lastTextNode.getContent() == null) {
                lastTextNode.setContent("\n");
            } else lastTextNode.setContent(lastTextNode.getContent() + "\n");
        }
    }

    private static boolean isLink(Element element) {
        return element.is("a")
                && !element.hasClass("footnoteref")
                && element.hasAttr("href")
                && (!element.attr("href").contains("javascript")
                && !element.attr("href").startsWith("#")
        );
    }

    private static HyperlinkNode scrapLink(Element linkElement, String source) {
        String href = linkElement.attr("href");
        if (href.startsWith("/")) {
            href = source.substring(0, source.lastIndexOf('/')) + href;
        }
        Map<String, String> styling = StyleScraper.scrapStyles(linkElement);
        return new HyperlinkNode(linkElement.wholeText(), styling, href);
    }
}
