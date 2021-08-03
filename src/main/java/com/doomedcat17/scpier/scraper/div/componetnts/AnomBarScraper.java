package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ParagraphNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import com.doomedcat17.scpier.scraper.text.TextScraper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class AnomBarScraper extends DivScraper implements DivScraperComponent {


    public AnomBarScraper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        contentNodes.add(scrapObjectName(element));
        Element scpClassesElement = element.selectFirst(".text-part");
        contentNodes.addAll(scrapScpClasses(scpClassesElement));
        return contentNodes;
    }

    private ContentNode<List<TextNode>> scrapObjectName(Element element)  {
        ParagraphNode paragraph = new ParagraphNode();
        Element itemHeaderElement = element.selectFirst(".item");
        //in some cases (SCP-PL-010) itemHeader is null
        if (itemHeaderElement != null && !itemHeaderElement.wholeText().isBlank()) {
            TextNode itemHeader = TextScraper.scrapText(itemHeaderElement, source).get(0);
            if (!itemHeader.getContent().endsWith(" ")) itemHeader.setContent(itemHeader.getContent() + " ");
            if (!itemHeader.getStyles().containsKey("font-weight")) itemHeader.addStyle("font-weight", "bold");
            paragraph.addElement(itemHeader);
        } else {
            TextNode generatedItemHeader = new TextNode("Item: ");
            generatedItemHeader.getStyles().put("font-weight", "bold");
            paragraph.addElement(generatedItemHeader);
        }
        Element itemNumberElement = element.selectFirst(".number");
        TextNode itemName = TextScraper.scrapText(itemNumberElement, source).get(0);
        if (!itemName.getContent().toLowerCase().startsWith("scp-")) itemName.setContent("SCP-"+itemName.getContent());
        paragraph.addElement(itemName);
        return paragraph;
    }

    private List<ParagraphNode> scrapScpClasses(Element scpClassesElement) {
        List<ParagraphNode> scpClasses = new ArrayList<>();
        for (Element divElement: scpClassesElement.children()) {
            if (divElement.is(".main-class")) {
                List<ParagraphNode> innerScpClasses = scrapScpClasses(divElement);
                innerScpClasses.stream()
                        .filter(innerScpClass -> !innerScpClass.getContent().isEmpty()).forEach(scpClasses::add);
            } else {
                ParagraphNode scrappedScpClass = scrapScpClass(divElement);
                if (!scrappedScpClass.isEmpty()) scpClasses.add(scrappedScpClass);
            }
        }
        return scpClasses;
    }
    private ParagraphNode scrapScpClass(Element scpClassElement) {
        ParagraphNode paragraph = new ParagraphNode();
        String scpClassName = scpClassElement.selectFirst(".class-text").text();
        if (!scpClassName.isBlank() && !scpClassName.equals("none") && !scpClassName.startsWith("{$")) {
            scpClassName = capitalizeText(scpClassName).stripTrailing();
            TextNode titleNode = new TextNode(scpClassElement.selectFirst(".class-category").text()+" ");
            titleNode.addStyle("font-weight", "bold");
            paragraph.addElement(titleNode);
            paragraph.addElement(new TextNode(scpClassName));
        }
        return paragraph;
    }

}
