package com.doomedcat17.scpier.scrapper.htmlscrappers.image;

import com.doomedcat17.scpier.data.content_node.ImageNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;

public class ImageScrapper extends ElementScrapper {
    public ImageScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public Appendix scrapElement(Element element) {
        Appendix appendix = new Appendix();
        ImageNode imageNode = new ImageNode();
        String imageSource = element
                .attributes().get("src");
        imageNode.setContent(imageSource);
        appendix.addContentNode(imageNode);
        return appendix;
    }
}
