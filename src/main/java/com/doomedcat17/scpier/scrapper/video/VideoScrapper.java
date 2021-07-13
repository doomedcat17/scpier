package com.doomedcat17.scpier.scrapper.video;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.VideoNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import org.jsoup.nodes.Element;

public class VideoScrapper extends ElementScrapper {
    public VideoScrapper(String source) {
        super(source);
    }
    @Override
    public ContentNode<?> scrapElement(Element element)  {
        try {
            Element sourceElement = element.selectFirst("source");
            source = sourceElement.attr("src");
            return new VideoNode(source);
        } catch (Exception e) {
            throw new ElementScrapperException(e.getMessage());
        }
    }

}