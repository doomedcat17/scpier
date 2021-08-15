package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.ListNode;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class DefaultDivScraper extends DivScraper implements DivScraperComponent {

    public DefaultDivScraper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        List<ContentNode<?>> contentNodes = ElementContentScraper.scrapContent(element, source);
        //in specific cases, div content is extracted
        if (element.is("center, robot, .list-pages-box, .list-pages-item")) {
            return contentNodes;
        } else {
            ListNode<ContentNode<?>> divNode = new ListNode<>(ContentNodeType.DIV);
            divNode.setContent(contentNodes);
            if (divNode.getContent().size() == 1) return new ArrayList<>(List.of(divNode.getContent().get(0)));
            return new ArrayList<>(List.of(divNode));
        }
    }
}
