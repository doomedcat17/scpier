package com.doomedcat17.scpier.scrapper.heading;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
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
            ContentNode<List<TextNode>> headingContentNode = new ContentNode<>(ContentNodeType.HEADING);
            List<TextNode> textNodes = TextScrapper.scrapText(element, source);
            headingContentNode.setContent(textNodes);
            return headingContentNode;
        } catch (Exception e) {
            throw new ElementScrapperException(e.getMessage());
        }
    }
}
