package com.doomedcat17.scpier.scraper.image;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.EmbedNode;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.scraper.ElementScraper;
import org.jsoup.nodes.Element;

public class ImageScraper extends ElementScraper {
    public ImageScraper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element)  {
        if (element.is("picture, figure, a")) element = element.selectFirst("img");
        try {
            String imageSource = element
                    .attributes().get("src");
            return new EmbedNode(ContentNodeType.IMAGE, imageSource);
        } catch (Exception e) {
            throw new ElementScraperException(e);
        }
    }
}
