package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.HeadingNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import com.doomedcat17.scpier.scrapper.ScrapperFactory;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class CollapsibleBlockScrapper extends DivScrapper implements DivScrapperComponent {
    public CollapsibleBlockScrapper(String source) {
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
            ElementScrapper elementScrapper = ScrapperFactory.getHtmlScrapper(collapsibleBlockContent.children().get(0), source);
            return List.of(elementScrapper.scrapElement(collapsibleBlockContent.children().get(0)));
        }
        List<ContentNode<?>> contentNodes = ElementContentScrapper.scrapContent(collapsibleBlockContent, source);
        blockContent.addAll(contentNodes);
        return blockContent;
    }

    private List<ContentNode<?>> scrapColmodConetnt(Element element)  {
        Element content = element.selectFirst(".colmod-content");
        if (content.children().size() == 1) {
            return ElementContentScrapper.scrapContent(content.children().get(0), source);
        } else return ElementContentScrapper.scrapContent(content, source);
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
