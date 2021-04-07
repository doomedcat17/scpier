package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class AnomBarScrapper extends DivScrapper implements DivScrapperComponent {


    public AnomBarScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) {
        Element scpClassesElement = element.selectFirst(".text-part");
        return new ArrayList<>(scrapScpClasses(scpClassesElement));
    }

    private List<ContentNode<List<TextNode>>> scrapScpClasses(Element scpClassesElement) {
        List<ContentNode<List<TextNode>>> scpClasses = new ArrayList<>();
        for (Element divElement: scpClassesElement.children()) {
            if (divElement.is(".main-class")) {
                List<ContentNode<List<TextNode>>> innerScpClasses = scrapScpClasses(divElement);
                innerScpClasses.stream()
                        .filter(innerScpClass -> !innerScpClass.getContent().isEmpty()).forEach(scpClasses::add);
            } else {
                ContentNode<List<TextNode>> scrappedScpClass = scrapScpClass(divElement);
                if (!scrappedScpClass.getContent().isEmpty()) scpClasses.add(scrappedScpClass);
            }
        }
        return scpClasses;
    }
    private ContentNode<List<TextNode>> scrapScpClass(Element scpClassElement) {
        ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
        String scpClassName = scpClassElement.selectFirst(".class-text").text();
        if (!scpClassName.equals("none")) {
            scpClassName = capitalizeText(scpClassName).stripTrailing();
            TextNode titleNode = new TextNode(scpClassElement.selectFirst(".class-category").text()+" ");
            titleNode.addStyle("font-weight", "bold");
            paragraph.getContent().add(titleNode);
            paragraph.getContent().add(new TextNode(scpClassName));
        }
        return paragraph;
    }

}
