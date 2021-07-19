package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.*;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class AdultWarningScraper extends DivScraper implements DivScraperComponent {
    public AdultWarningScraper(String source) {
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
        divNode.addElements(ElementContentScraper.scrapContent(element, source));
        return new ArrayList<>(List.of(divNode));
    }
}
