package com.doomedcat17.scpier.scrapper.htmlscrappers.video;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.VideoNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import org.jsoup.nodes.Element;

public class VideoScrapper extends ElementScrapper {
    public VideoScrapper(String source) {
        super(source);
    }
    @Override
    public ContentNode<?> scrapElement(Element element) {
        Element sourceElement = element.selectFirst("source");
        source = sourceElement.attr("src");
        return new VideoNode(source);
    }

}
