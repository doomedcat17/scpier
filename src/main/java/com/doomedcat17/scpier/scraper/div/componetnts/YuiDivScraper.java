package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class YuiDivScraper extends DivScraper implements DivScraperComponent {
    public YuiDivScraper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        if (element.is(".yui-navset")) {
            Element yuiContent = element.selectFirst(".yui-content");
            if (yuiContent != null) {
                Element yuiNav = element.selectFirst(".yui-nav");
                for (int i = 0; i < yuiNav.childrenSize(); i++) {
                    String title = yuiNav.children().get(i).text();
                    Element contentTab = yuiContent.selectFirst("#wiki-tab-0-"+i);
                    Element heading = new Element("h2");
                    heading.appendChild(new TextNode(title));
                    contentTab.insertChildren(0, heading);
                }
                contentNodes.addAll(scrapDivContent(yuiContent));
            }
        } else if (element.is(".yui-content")) {
            List<Element> filteredElements =  element.children().stream()
                    .filter(wikiTab -> wikiTab.hasAttr("id"))
                    .filter(wikiTab -> wikiTab.attr("id").contains("wiki-tab-"))
                    .collect(Collectors.toList());
            for (Element innerElement : filteredElements) {
                contentNodes.addAll(scrapWikiTab(innerElement));
            }
        } else if (element.attr("id").contains("wiki-tab-")) contentNodes.addAll(scrapWikiTab(element));
        return contentNodes;
    }

    private List<ContentNode<?>> scrapWikiTab(Element wikiTab)  {
        wikiTab.attr("id", "page-content");
        return ElementContentScraper.scrapContent(wikiTab, source);
    }
}
