package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.exceptions.MapperNotFoundException;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class ElementScrapper {

    protected ElementScrapper(String source) {
        this.source = source;
    }

    public abstract ContentNode<?> scrapElement(Element element);

    protected String source;

    public List<ContentNode<?>> scrapContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        for (Node node : element.childNodes()) {
            try {
                if (node instanceof Element) {
                    Element childElement = (Element) node;
                    if (childElement.childNodes().isEmpty() || node.toString().isBlank()) continue;
                    ElementScrapper elementScrapper = HtmlScrapperFactory.getHtmlScrapper(childElement, source);
                    ContentNode<?> contentNode = elementScrapper.scrapElement(childElement);
                    if (!contentNode.isEmpty()) {
                        if (contentNode.getContentNodeType().equals(ContentNodeType.PARAGRAPHS) || contentNode.getContentNodeType().equals(ContentNodeType.CONTENT_NODES)) {
                            List<ContentNode<?>> paragraphs = (List<ContentNode<?>>) contentNode.getContent();
                            contentNodes.addAll(paragraphs);
                        } else contentNodes.add(contentNode);
                    }
                } else {
                    String text = node.toString();
                    if (!text.isBlank()) {
                        if(!contentNodes.isEmpty()) {
                            if (contentNodes.get(contentNodes.size() - 1).getContentNodeType().equals(ContentNodeType.PARAGRAPH)) {
                                ContentNode<List<TextNode>> lastParagraph = (ContentNode<List<TextNode>>) contentNodes.get(contentNodes.size() - 1);
                                if (text.charAt(0) == ' ') {
                                    lastParagraph.getContent().add(new TextNode(text));
                                    continue;
                                }
                            }
                        }
                        ContentNode<List<TextNode>> paragraph =
                                new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>(List.of(new TextNode(text.trim()))));
                        contentNodes.add(paragraph);
                    }
                }
            } catch (MapperNotFoundException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return contentNodes;
    }



    protected String capitalizeText(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}

