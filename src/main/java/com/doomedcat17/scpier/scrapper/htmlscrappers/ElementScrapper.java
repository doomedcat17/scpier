package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.exceptions.MapperNotFoundException;

import java.util.ArrayList;
import java.util.List;

public abstract class ElementScrapper {

    protected ElementScrapper(String source, TitleResolver titleResolver) {
        this.source = source;
        this.titleResolver = titleResolver;
    }

    public abstract Appendix scrapElement(Element element);

    protected String source;

    protected final TitleResolver titleResolver;

    public List<ContentNode<?>> scrapElementInnerContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        for (Node node : element.childNodes()) {
            try {
                if (node instanceof Element) {
                    Element childElement = (Element) node;
                    if (childElement.childNodes().isEmpty() || node.toString().isBlank()) continue;
                    ElementScrapper elementScrapper = HtmlScrapperFactory.getHtmlScrapper(childElement, source, titleResolver);
                    Appendix appendix = elementScrapper.scrapElement(childElement);
                    if (appendix.hasTitle()) {
                        ContentNode<List<TextNode>> headingTextNodes = new ContentNode<>(ContentNodeType.HEADING, new ArrayList<>());
                        headingTextNodes.getContent().add(new TextNode(appendix.getTitle()));
                        contentNodes.add(headingTextNodes);
                    }
                    contentNodes.addAll(appendix.getContents());
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
            }
        }
        return contentNodes;
    }



    protected String capitalizeText(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}

