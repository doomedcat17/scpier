package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.appendix.Appendix;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class AdultWarningScrapper extends DivScrapper implements DivScrapperComponent {
    public AdultWarningScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }
    @Override
    public List<Appendix> scrapDivContent(Element element) {
        Appendix appendix = new Appendix();
        Element headingElement = element.getElementById("u-adult-header");
        appendix.setTitle(headingElement.text());
        headingElement.remove();
        appendix.setContents(scrapElementInnerContent(element));
        return new ArrayList<>(List.of(appendix));
    }
}
