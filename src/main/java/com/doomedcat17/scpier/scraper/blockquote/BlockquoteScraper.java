package com.doomedcat17.scpier.scraper.blockquote;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.ListNode;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.ElementScraper;
import org.jsoup.nodes.Element;

import java.util.List;

public class BlockquoteScraper extends ElementScraper {
    public BlockquoteScraper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element)  {
        try {
            ListNode<ContentNode<?>> blockquoteNode = new ListNode<>(ContentNodeType.BLOCKQUOTE);
            List<ContentNode<?>> contentNodes = ElementContentScraper.scrapContent(element, source);
            blockquoteNode.setContent(contentNodes);
            return blockquoteNode;
        } catch (Exception e) {
            throw new ElementScraperException(e);
        }
    }
}
