package com.doomedcat17.scpier.scrapper.image;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.EmbedNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import org.jsoup.nodes.Element;

public class ImageScrapper extends ElementScrapper {
    public ImageScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element)  {
        try {
            String imageSource = element
                    .attributes().get("src");
            return new EmbedNode(ContentNodeType.IMAGE, imageSource);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ElementScrapperException(e.getMessage());
        }
    }
}
