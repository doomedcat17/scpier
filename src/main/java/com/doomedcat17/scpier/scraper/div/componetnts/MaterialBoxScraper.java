package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.ListNode;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class MaterialBoxScraper extends DivScraper implements DivScraperComponent {
    public MaterialBoxScraper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        return new ArrayList<>(List.of(new ListNode<>(ContentNodeType.DIV, ElementContentScraper.scrapContent(element, source))));
    }
}
