package com.doomedcat17.scpier.scraper;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.ParagraphNode;
import com.doomedcat17.scpier.data.content.TextNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class ElementContentScraper {

    public static List<ContentNode<?>> scrapContent(Element element, String source)  {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        for (Node node : element.childNodes()) {
            if (node instanceof Element) {
                Element childElement = (Element) node;
                ElementScraper elementScraper = ScraperFactory.getHtmlScrapper(childElement, source);
                ContentNode<?> contentNode = elementScraper.scrapElement(childElement);
                if (!contentNode.isEmpty()) {
                    if (contentNode.getContentNodeType().equals(ContentNodeType.PARAGRAPHS) || contentNode.getContentNodeType().equals(ContentNodeType.CONTENT_NODES)) {
                        List<ContentNode<?>> paragraphs = (List<ContentNode<?>>) contentNode.getContent();
                        contentNodes.addAll(paragraphs);
                    } else contentNodes.add(contentNode);
                }
            } else {
                String text = node.toString();
                if (!text.isBlank()) {
                    if (!contentNodes.isEmpty()) {
                        if (contentNodes.get(contentNodes.size() - 1) instanceof ParagraphNode) {
                            ParagraphNode lastParagraph = (ParagraphNode) contentNodes.get(contentNodes.size() - 1);
                            if (text.charAt(0) == ' ') {
                                lastParagraph.addElement(new TextNode(text));
                                continue;
                            }
                        }
                    }
                    contentNodes.add(new ParagraphNode(new ArrayList<>(List.of(new TextNode(text.stripLeading())))));
                }
            }

        }
        return contentNodes;
    }
}
