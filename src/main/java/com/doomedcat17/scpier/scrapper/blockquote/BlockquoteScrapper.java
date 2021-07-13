package com.doomedcat17.scpier.scrapper.blockquote;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import org.jsoup.nodes.Element;

import java.util.List;

public class BlockquoteScrapper extends ElementScrapper {
    public BlockquoteScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) throws ElementScrapperException {
        try {
            ContentNode<List<ContentNode<?>>> blockquoteNode = new ContentNode<>(ContentNodeType.BLOCKQUOTE);
            List<ContentNode<?>> contentNodes = ElementContentScrapper.scrapContent(element, source);
            blockquoteNode.setContent(contentNodes);
            return blockquoteNode;
        } catch (Exception e) {
            throw new ElementScrapperException(e.getMessage());
        }
    }
}
