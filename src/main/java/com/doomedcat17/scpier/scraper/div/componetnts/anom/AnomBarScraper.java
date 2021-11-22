package com.doomedcat17.scpier.scraper.div.componetnts.anom;

import com.doomedcat17.scpier.data.content.*;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import com.doomedcat17.scpier.scraper.div.componetnts.DivScraperComponent;
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

    private ContentNode<List<TextNode>> scrapObjectName(Element element) {
        ParagraphNode paragraph = new ParagraphNode();
        Element itemHeaderElement = element.selectFirst(".item");
        //in some cases (SCP-PL-010) itemHeader is null
        if (itemHeaderElement != null && !itemHeaderElement.wholeText().isBlank()) {
            TextNode itemHeader = TextScraper.scrap(itemHeaderElement, source).get(0);
            if (!itemHeader.getContent().endsWith(" ")) itemHeader.setContent(itemHeader.getContent() + " ");
            if (!itemHeader.getStyles().containsKey("font-weight")) itemHeader.addStyle("font-weight", "bold");
            paragraph.addElement(itemHeader);
        } else {
            AnomBarHeader anomBarHeader = AnomBarHeader.getAnomBarHeaderByUrl(source);
            String scpItemHeader = anomBarHeader.itemName;
            TextNode generatedItemHeader = new TextNode(scpItemHeader + " ");
            generatedItemHeader.getStyles().put("font-weight", "bold");
            paragraph.addElement(generatedItemHeader);
        }
        Element itemNumberElement = element.selectFirst(".number");
        TextNode itemName = TextScraper.scrap(itemNumberElement, source).get(0);
        if (!itemName.getContent().toLowerCase().startsWith("scp-"))
            itemName.setContent("SCP-" + itemName.getContent());
        paragraph.addElement(itemName);
        return paragraph;
    }

    private List<ParagraphNode> scrapScpClasses(Element scpClassesElement) {
        List<ParagraphNode> scpClasses = new ArrayList<>();
        for (Element divElement : scpClassesElement.children()) {
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
        if (scpClassElement.wholeText().isBlank()) {
            scrapScpClassFromStyling(scpClassElement, paragraph);
        } else {
            String scpClassName = scpClassElement.selectFirst(".class-text").text();
            if (!scpClassName.isBlank() && !scpClassName.equals("none") && !scpClassName.startsWith("{$")) {
                scpClassName = capitalizeText(scpClassName).stripTrailing();
                Element titleElement = scpClassElement.selectFirst(".class-category");
                //chinese translations have two versions with one not displayed
                if (!titleElement.children().isEmpty()) {
                    titleElement = titleElement.children().get(0);
                }
                TextNode titleNode = new TextNode(titleElement.text() + " ");
                titleNode.addStyle("font-weight", "bold");
                paragraph.addElement(titleNode);
                paragraph.addElement(new TextNode(scpClassName));
            }
        }
        return paragraph;
    }

    private void scrapScpClassFromStyling(Element scpClassElement, ParagraphNode paragraphNode) {
        try {
            scpClassElement = scpClassElement.selectFirst(".class-text");
            String classesString = scpClassElement.attr("class");
            AnomBarHeader anomBarHeader = AnomBarHeader.getAnomBarHeaderByUrl(source);
            String scpClassName = anomBarHeader.getByStyleName(classesString);
            classesString = scpStyleClassesCleanup(classesString);
            String className = classesString.substring(0, 1).toUpperCase() + classesString.substring(1);
            TextNode scpClassNameNode = new TextNode(scpClassName + " ");
            scpClassNameNode.getStyles().put("font-weight", "bold");
            paragraphNode.addElement(scpClassNameNode);
            paragraphNode.addElement(new TextNode(className));
        } catch (IllegalArgumentException ignored) {

        }
    }

    private String scpStyleClassesCleanup(String styleScpClasses) {
        styleScpClasses = styleScpClasses.replaceAll("\\{\\S+", "")
                .replaceAll("class-text", "")
                .replaceAll("\\d", "");
        String langId = AnomBarHeader.getAnomBarHeaderByUrl(source).langIdentifier;
        return styleScpClasses.replaceAll("(?<!\\w)"+langId+"(?!\\w)", "").trim();
    }

}
