package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class DefaultDivScrapper extends DivScrapper implements DivScrapperComponent {

    public DefaultDivScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) throws ElementScrapperException {
        List<ContentNode<?>> contentNodes = ElementContentScrapper.scrapContent(element, source);
        ContentNode<List<ContentNode<?>>> divNode = new ContentNode<>(ContentNodeType.DIV);
        divNode.setContent(contentNodes);
        if (divNode.getContent().size() == 1) return new ArrayList<>(List.of(divNode.getContent().get(0)));
        return new ArrayList<>(List.of(divNode));
    }
}
