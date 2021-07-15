package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ParagraphNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.text.TextScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class AnomBarScrapper extends DivScrapper implements DivScrapperComponent {


    public AnomBarScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        contentNodes.add(scrapItemName(element));
        Element scpClassesElement = element.selectFirst(".text-part");
        contentNodes.addAll(scrapScpClasses(scpClassesElement));
        return contentNodes;
    }

    private ContentNode<List<TextNode>> scrapItemName(Element element)  {
        ParagraphNode paragraph = new ParagraphNode();
        Element itemHeaderElement = element.selectFirst(".item");
        TextNode itemHeader = TextScrapper.scrapText(itemHeaderElement, source).get(0);
        if (!itemHeader.getContent().endsWith(" ")) itemHeader.setContent(itemHeader.getContent()+" ");
        if (!itemHeader.getStyles().containsKey("font-weight")) itemHeader.addStyle("font-weight", "bold");
        paragraph.addElement(itemHeader);
        Element itemNumberElement = element.selectFirst(".number");
        TextNode itemName = TextScrapper.scrapText(itemNumberElement, source).get(0);
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
        if (!scpClassName.equals("none")) {
            scpClassName = capitalizeText(scpClassName).stripTrailing();
            TextNode titleNode = new TextNode(scpClassElement.selectFirst(".class-category").text()+" ");
            titleNode.addStyle("font-weight", "bold");
            paragraph.addElement(titleNode);
            paragraph.addElement(new TextNode(scpClassName));
        }
        return paragraph;
    }

}
