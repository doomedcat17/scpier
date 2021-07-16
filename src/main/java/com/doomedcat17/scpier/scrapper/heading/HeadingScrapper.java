package com.doomedcat17.scpier.scrapper.heading;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.HeadingNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import com.doomedcat17.scpier.scrapper.text.TextScrapper;
import org.jsoup.nodes.Element;

import java.util.List;

public class HeadingScrapper extends ElementScrapper {
    public HeadingScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element)  {
        try {
            HeadingNode headingNode = new HeadingNode();
            List<TextNode> textNodes = TextScrapper.scrapText(element, source);
            headingNode.setContent(textNodes);
            return headingNode;
        } catch (Exception e) {
            throw new ElementScrapperException(e.getMessage());
        }
    }
}
