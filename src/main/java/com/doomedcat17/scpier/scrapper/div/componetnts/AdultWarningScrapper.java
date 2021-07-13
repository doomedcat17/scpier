package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class AdultWarningScrapper extends DivScrapper implements DivScrapperComponent {
    public AdultWarningScrapper(String source) {
        super(source);
    }
    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        Element headingElement = element.getElementById("u-adult-header");
        ContentNode<List<TextNode>> headingNode = new ContentNode<>(ContentNodeType.HEADING, new ArrayList<>());
        headingNode.getContent().add(new TextNode(headingElement.text()));
        headingElement.remove();
        ContentNode<List<ContentNode<?>>> divNode = new ContentNode<>(ContentNodeType.DIV, new ArrayList<>());
        divNode.getContent().add(headingNode);
        divNode.getContent().addAll(ElementContentScrapper.scrapContent(element, source));
        return new ArrayList<>(List.of(divNode));
    }
}
