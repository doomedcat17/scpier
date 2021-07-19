package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.HeadingNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.ElementScraper;
import com.doomedcat17.scpier.scraper.ScraperFactory;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class CollapsibleBlockScraper extends DivScraper implements DivScraperComponent {
    public CollapsibleBlockScraper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        if (element.is(".colmod-block")) return scrapColmodConetnt(element);
        List<ContentNode<?>> blockContent = new ArrayList<>();
        String title = element.selectFirst(".collapsible-block-link").text();
        title = clearCollapsibleBlockTittle(title);
        HeadingNode heading = new HeadingNode();
        TextNode titleNode = new TextNode(title);
        titleNode.addStyle("font-weight", "bold");
        heading.addElement(titleNode);
        blockContent.add(heading);
        Element collapsibleBlockContent = element.selectFirst(".collapsible-block-content");
        if (collapsibleBlockContent.children().size() == 1) {
            ElementScraper elementScraper = ScraperFactory.getHtmlScrapper(collapsibleBlockContent.children().get(0), source);
            return List.of(elementScraper.scrapElement(collapsibleBlockContent.children().get(0)));
        }
        List<ContentNode<?>> contentNodes = ElementContentScraper.scrapContent(collapsibleBlockContent, source);
        blockContent.addAll(contentNodes);
        return blockContent;
    }

    private List<ContentNode<?>> scrapColmodConetnt(Element element)  {
        Element content = element.selectFirst(".colmod-content");
        if (content.children().size() == 1) {
            return ElementContentScraper.scrapContent(content.children().get(0), source);
        } else return ElementContentScraper.scrapContent(content, source);
    }

    private String clearCollapsibleBlockTittle(String title){
        if (!title.isBlank()) {
            char firstChar = title.charAt(0);
            if (firstChar == '+' || firstChar == '>' || firstChar == '-') {
                return title.substring(1).trim();
            }
        }
        return title;
    }
}
