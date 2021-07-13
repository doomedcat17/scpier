package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class MaterialBoxScrapper extends DivScrapper implements DivScrapperComponent {
    public MaterialBoxScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        return new ArrayList<>(List.of(new ContentNode<>(ContentNodeType.DIV, ElementContentScrapper.scrapContent(element, source))));
    }
}
