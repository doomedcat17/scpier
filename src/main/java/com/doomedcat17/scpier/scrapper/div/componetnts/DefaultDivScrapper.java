package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.ListNode;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class DefaultDivScrapper extends DivScrapper implements DivScrapperComponent {

    public DefaultDivScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        List<ContentNode<?>> contentNodes = ElementContentScrapper.scrapContent(element, source);
        ListNode<ContentNode<?>> divNode = new ListNode<>(ContentNodeType.DIV);
        divNode.setContent(contentNodes);
        if (divNode.getContent().size() == 1) return new ArrayList<>(List.of(divNode.getContent().get(0)));
        return new ArrayList<>(List.of(divNode));
    }
}
