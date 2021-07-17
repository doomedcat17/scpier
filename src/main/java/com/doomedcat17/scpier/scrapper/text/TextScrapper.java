package com.doomedcat17.scpier.scrapper.text;

import com.doomedcat17.scpier.data.contentnode.HyperlinkNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextScrapper {

    public static List<TextNode> scrapText(Element textElement, String source) {
        try {
            List<TextNode> textNodes = new ArrayList<>();
            Map<String, String> elementStyles = StyleScrapper.scrapStyles(textElement);
            if (textElement.is("br")) {
                textNodes.add(new TextNode("\n"));
                return textNodes;
            } else if (textElement.is("a") && !textElement.hasClass("footnoteref")
                    && textElement.hasAttr("href")
                    && (!textElement.attr("href").equals("#") && !textElement.attr("href").contains("javascript"))) {
                return new ArrayList<>(List.of(scrapLink(textElement, source, elementStyles)));
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
                        textNodes.add(scrapLink(innerElement, source, elementStyles));
                    } else {
                        if (innerElement.text().isBlank()) continue;
                        //retrieve element styles
                        Map<String, String> styles = getAppliedStyles(innerElement, elementStyles);
                        if (innerElement.childrenSize() != 0) {
                            textNodes.addAll(mapInnerTextElements(innerElement, styles, source));
                        } else {
                            String text = innerElement.wholeText();
                            TextNode textNode = new TextNode(text, styles);
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
        } catch (Exception e) {
            throw new ElementScrapperException(e.getMessage());
        }
    }

    private static Map<String, String> getAppliedStyles(Element element, Map<String, String> parentStyles) {
        //retrieve element styles
        Map<String, String> innerElementStyles = StyleScrapper.scrapStyles(element);
        Map<String, String> styles;
        if (!parentStyles.isEmpty()) {
            //inherit parent styles
            styles = new HashMap<>(parentStyles);
            styles.putAll(innerElementStyles);
        } else styles = innerElementStyles;
        return styles;
    }

    private static List<TextNode> mapInnerTextElements(Element element, Map<String, String> parentStyles, String source) {
        List<TextNode> innerTextNodes = new ArrayList<>();
        for (Node innerNode : element.childNodes()) {
            if (innerNode instanceof Element) {
                List<TextNode> nodes = scrapText((Element) innerNode, source);
                innerTextNodes.addAll(nodes);
            } else {
                String text = innerNode.toString();
                if (!text.isBlank()) {
                    innerTextNodes.add(new TextNode(innerNode.toString()));
                }
            }
        }
        //add parent styles to children, but don't overwrite them
        parentStyles.forEach((key, value) -> {
            innerTextNodes.forEach(textNode -> {
                if (!textNode.getStyles().containsKey(key)) {
                    textNode.getStyles().put(key, value);
                }
            });
        });
        return innerTextNodes;
    }

    //styles of textnodes are ignored
    public static String margeTextNodes(List<TextNode> textNodes) {
        StringBuilder stringBuilder = new StringBuilder();
        textNodes.forEach(textNode -> stringBuilder.append(textNode.getContent()));
        return stringBuilder.toString();
    }

    private static HyperlinkNode scrapLink(Element linkElement, String source, Map<String, String> elementStyles) {
        String href = linkElement.attr("href");
        if (href.startsWith("/")) {
            href = source.substring(0, source.lastIndexOf('/')) + href;
        }
        Map<String, String> styles = getAppliedStyles(linkElement, elementStyles);
        return new HyperlinkNode(linkElement.wholeText(), styles, href);
    }
}
