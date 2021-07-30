package com.doomedcat17.scpier.scraper.div;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.ListNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scraper.ElementScraper;
import com.doomedcat17.scpier.scraper.div.componetnts.DivScraperComponent;
import org.jsoup.nodes.Element;

import java.util.List;

public class DivScraper extends ElementScraper {

    public DivScraper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element)  {
        try {
            List<ContentNode<?>> contentNodes = scrap(element);
            if (contentNodes.size() == 1) {
                return contentNodes.get(0);
            } else return new ListNode<>(ContentNodeType.CONTENT_NODES, contentNodes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ElementScrapperException(e.getMessage());
        }
    }

    protected List<ContentNode<?>> scrap(Element element)  {
        DivScraperComponent divMapperComponent = DivScraperComponentFactory.getDivScrapperComponent(element, source);
        return divMapperComponent.scrapDivContent(element);
    }
}
