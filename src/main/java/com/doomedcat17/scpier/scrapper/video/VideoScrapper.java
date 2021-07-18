package com.doomedcat17.scpier.scrapper.video;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.EmbedNode;
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
            return new EmbedNode(ContentNodeType.VIDEO, source);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ElementScrapperException(e.getMessage());
        }
    }

}
