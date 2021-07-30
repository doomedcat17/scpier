package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ParagraphNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;


public class ENbaseDivScraper extends DivScraper implements DivScraperComponent {
    public ENbaseDivScraper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        contentNodes.add(scrapObjectName(element));
        contentNodes.add(scrapObjectClass(element));
        return contentNodes;
    }

    private ContentNode<List<TextNode>> scrapObjectName(Element element) {
        String itemText = element.getElementsByClass("itemnum").get(0).text();
        String[] itemStrings = itemText.split(":");
        TextNode itemHeading = new TextNode(itemStrings[0] + ": ");
        itemHeading.addStyle("font-weight", "bold");
        TextNode itemName = new TextNode(itemStrings[1].trim());
        return new ParagraphNode(new ArrayList<>(List.of(itemHeading, itemName)));
    }

    private ContentNode<List<TextNode>> scrapObjectClass(Element element) {
        String scpClassName = element.selectFirst(".obj-text").text();
        TextNode objectClassName = new TextNode(capitalizeText(scpClassName));
        TextNode objectClass = new TextNode("Object Class: ");
        objectClass.addStyle("font-weight", "bold");
        return new ParagraphNode(new ArrayList<>(List.of(objectClass, objectClassName)));
    }

}
