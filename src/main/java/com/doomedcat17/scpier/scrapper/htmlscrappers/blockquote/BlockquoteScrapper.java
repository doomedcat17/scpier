package com.doomedcat17.scpier.scrapper.htmlscrappers.blockquote;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import org.jsoup.nodes.Element;

import java.util.List;

public class BlockquoteScrapper extends ElementScrapper {
    public BlockquoteScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        ContentNode<List<ContentNode<?>>> blockquoteNode = new ContentNode<>(ContentNodeType.BLOCKQUOTE);
        List<ContentNode<?>> contentNodes = scrapContent(element);
        blockquoteNode.setContent(contentNodes);
       return blockquoteNode;
    }
}
