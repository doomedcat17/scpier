package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.*;
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
        HeadingNode headingNode = new HeadingNode();
        headingNode.getContent().add(new TextNode(headingElement.text()));
        headingElement.remove();
        ListNode<ContentNode<?>> divNode = new ListNode<>(ContentNodeType.DIV);
        divNode.addElement(headingNode);
        divNode.addElements(ElementContentScrapper.scrapContent(element, source));
        return new ArrayList<>(List.of(divNode));
    }
}
