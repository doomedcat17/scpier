package com.doomedcat17.scpier.scrapper.htmlscrappers.video;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.VideoNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import org.jsoup.nodes.Element;

public class VideoScrapper extends ElementScrapper {
    public VideoScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }
    @Override
    public Appendix scrapElement(Element element) {
        Element sourceElement = element.selectFirst("source");
        source = sourceElement.attr("src");
        VideoNode videoNode = new VideoNode(source);
        Appendix appendix = new Appendix();
        appendix.addContentNode(videoNode);
        return appendix;
    }

}
