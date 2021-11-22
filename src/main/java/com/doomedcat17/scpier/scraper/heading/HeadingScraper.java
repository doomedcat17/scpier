package com.doomedcat17.scpier.scraper.heading;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.HeadingNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.scraper.ElementScraper;
import com.doomedcat17.scpier.scraper.text.TextScraper;
import org.jsoup.nodes.Element;

import java.util.List;

public class HeadingScraper extends ElementScraper {
    public HeadingScraper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element)  {
        try {
            HeadingNode headingNode = new HeadingNode();
            List<TextNode> textNodes = TextScraper.scrap(element, source);
            headingNode.setContent(textNodes);
            return headingNode;
        } catch (Exception e) {
            throw new ElementScraperException(e);
        }
    }
}
