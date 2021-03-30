package com.doomedcat17.scpier.scrapper.htmlscrappers.blockquote;

import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;

import java.util.List;

public class BlockquoteScrapper extends ElementScrapper {
    public BlockquoteScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public Appendix scrapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<ContentNode<?>>> blockquoteNode = new ContentNode<>(ContentNodeType.BLOCKQUOTE);
        List<ContentNode<?>> contentNodes = scrapContent(element);
        blockquoteNode.setContent(contentNodes);
        appendix.addContentNode(blockquoteNode);
       return appendix;
    }
}
