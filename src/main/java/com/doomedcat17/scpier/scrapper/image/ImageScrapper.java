package com.doomedcat17.scpier.scrapper.image;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ImageNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import org.jsoup.nodes.Element;

public class ImageScrapper extends ElementScrapper {
    public ImageScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) throws ElementScrapperException {
        try {
            ImageNode imageNode = new ImageNode();
            String imageSource = element
                    .attributes().get("src");
            imageNode.setContent(imageSource);
            return imageNode;
        } catch (Exception e) {
            throw new ElementScrapperException(e.getMessage());
        }
    }
}
