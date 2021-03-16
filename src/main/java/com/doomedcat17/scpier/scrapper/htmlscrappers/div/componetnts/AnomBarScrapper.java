package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;

import java.util.ArrayList;
import java.util.List;

public class AnomBarScrapper extends DivScrapper implements DivScrapperComponent {


    public AnomBarScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        Element scpClassesElement = element.selectFirst(".text-part");
        return new ArrayList<>(scrapScpClasses(scpClassesElement));
    }

    private List<Appendix> scrapScpClasses(Element scpClassesElement) {
        List<Appendix> scpClassesAppendices = new ArrayList<>();
        scpClassesElement.children().forEach(divElement -> {
            if (divElement.is(".main-class")) {
                scpClassesAppendices.addAll(scrapScpClasses(divElement));
            } else {
                Appendix scrappedScpClass = scrapScpClass(divElement);
                if (scrappedScpClass.hasTitle()) scpClassesAppendices.add(scrappedScpClass);
            }
        });
        return scpClassesAppendices;
    }
    private Appendix scrapScpClass(Element scpClassElement) {
        Appendix scpClassAppendix = new Appendix();
        String scpClassName = scpClassElement.selectFirst(".class-text").text();
        if (!scpClassName.equals("none")) {
            String scpClassTitle = removeColon(scpClassElement.selectFirst(".class-category").text());
            scpClassName = firstLetterToUpper(scpClassName);
            scpClassAppendix.setTitle(scpClassTitle);
            ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
            paragraph.getContent().add(new TextNode(scpClassName));
            scpClassAppendix.addContentNode(paragraph);
        }
        return scpClassAppendix;
    }

    private String removeColon(String text) {
        if (text.charAt(text.length() - 1) == ':') {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    private String firstLetterToUpper(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
