package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class YuiDivScrapper extends DivScrapper implements DivScrapperComponent {
    public YuiDivScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        if (element.hasClass("yui-navset")) {
            Element yuiContent = element.selectFirst(".yui-content");
            if (yuiContent != null) contentNodes.addAll(scrapDivContent(yuiContent));
        } else if (element.hasClass("yui-content")) {
            List<Element> filteredElements =  element.children().stream()
                    .filter(wikiTab -> wikiTab.hasAttr("id"))
                    .filter(wikiTab -> wikiTab.attr("id").contains("wiki-tab-")).collect(Collectors.toList());
            for (Element innerElement : filteredElements) {
                contentNodes.addAll(scrapWikiTab(innerElement));
            }
        } else if (element.attr("id").contains("wiki-tab-")) contentNodes.addAll(scrapWikiTab(element));
        return contentNodes;
    }

    private List<ContentNode<?>> scrapWikiTab(Element wikiTab)  {
        wikiTab.attr("id", "page-content");
        return ElementContentScrapper.scrapContent(wikiTab, source);
    }
}
