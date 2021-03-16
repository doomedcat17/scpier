package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNode;

import java.util.ArrayList;
import java.util.List;

public class CollapsibleBlockScrapper extends DivScrapper implements DivScrapperComponent {
    public CollapsibleBlockScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        Appendix appendix = new Appendix();
        String title = element.selectFirst(".collapsible-block-link").text();
        title = clearCollapsibleBlockTittle(title);
        if (titleResolver.isTitle(title)) appendix.setTitle(title);
        Element collapsibleBlockContent = element.selectFirst(".collapsible-block-content");
        List<ContentNode<?>> contentNodes = scrapElementInnerContent(collapsibleBlockContent);
        appendix.setContents(contentNodes);
        return new ArrayList<>(List.of(appendix));
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
