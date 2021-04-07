package com.doomedcat17.scpier.scrapper.htmlscrappers.image;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ImageNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import org.jsoup.nodes.Element;

public class ImageScrapper extends ElementScrapper {
    public ImageScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        ImageNode imageNode = new ImageNode();
        String imageSource = element
                .attributes().get("src");
        imageNode.setContent(imageSource);
        return imageNode;
    }
}
