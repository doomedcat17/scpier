package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.*;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CollapsibleBlockScraper extends DivScraper implements DivScraperComponent {
    public CollapsibleBlockScraper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        if (element.is(".colmod-block")) return scrapColmodConetnt(element);
        List<ContentNode<?>> divContent = new ArrayList<>();
        String title = element.selectFirst(".collapsible-block-link").text();
        title = clearCollapsibleBlockTittle(title);
        HeadingNode heading = new HeadingNode();
        TextNode titleNode = new TextNode(title);
        titleNode.addStyle("font-weight", "bold");
        heading.addElement(titleNode);
        divContent.add(heading);
        Element collapsibleBlockContent = element.selectFirst(".collapsible-block-content");
        Elements contentSiblings = collapsibleBlockContent.siblingElements();
        if (collapsibleBlockContent.children().isEmpty()) {
            // in some cases content is empty and its content is in element with "fadeintext" class
            collapsibleBlockContent = element.selectFirst(".fadeintext, .terminal");
            if (collapsibleBlockContent == null && contentSiblings.isEmpty()) return divContent;
        }
        if (collapsibleBlockContent != null) {
            List<ContentNode<?>> contentNodes = ElementContentScraper.scrapContent(collapsibleBlockContent, source);
            divContent.addAll(contentNodes);
        }
        if (!contentSiblings.isEmpty()) {
            divContent.addAll(scrapSiblingElements(contentSiblings));
        }
        return divContent;
    }

    private List<ContentNode<?>> scrapSiblingElements(Elements siblings) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        siblings.stream()
                .map(sibling -> ElementContentScraper.scrapContent(sibling, source))
                .forEach(contentNodes::addAll);
        return contentNodes;
    }

    private List<ContentNode<?>> scrapColmodConetnt(Element element)  {
        Element content = element.selectFirst(".colmod-content");
        List<ContentNode<?>> contentNodes = ElementContentScraper.scrapContent(content, source);
        if (contentNodes.size() == 1 &&
                contentNodes.get(0).getContentNodeType().equals(ContentNodeType.DIV)) {
            ListNode<ContentNode<?>> innerDivNode = (ListNode<ContentNode<?>>) contentNodes.get(0);
            return innerDivNode.getContent();
        } else return contentNodes;
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
