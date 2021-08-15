package com.doomedcat17.scpier.scraper.video;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.EmbedNode;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.scraper.ElementScraper;
import org.jsoup.nodes.Element;

public class VideoScraper extends ElementScraper {
    public VideoScraper(String source) {
        super(source);
    }
    @Override
    public ContentNode<?> scrapElement(Element element)  {
        try {
            Element sourceElement = element.selectFirst("source");
            source = sourceElement.attr("src");
            return new EmbedNode(ContentNodeType.VIDEO, source);
        } catch (Exception e) {
            throw new ElementScraperException(e);
        }
    }

}
