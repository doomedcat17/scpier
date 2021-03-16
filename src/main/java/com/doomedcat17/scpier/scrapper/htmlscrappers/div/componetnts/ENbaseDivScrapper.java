package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;

import java.util.ArrayList;
import java.util.List;


public class ENbaseDivScrapper extends DivScrapper implements DivScrapperComponent {
    public ENbaseDivScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        List<Appendix> appendices = new ArrayList<>();
        String scpClassName = element.getElementsByClass("obj-text")
                .get(0)
                .text().trim();
        Appendix classAppendix = new Appendix();
        classAppendix.setTitle("Object Class");
        classAppendix.addContentNode(new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>(List.of(new TextNode(capitalizeText(scpClassName))))));
        appendices.add(classAppendix);
        return appendices;
    }

}
