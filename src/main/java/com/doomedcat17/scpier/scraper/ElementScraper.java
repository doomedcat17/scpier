package com.doomedcat17.scpier.scraper;

import com.doomedcat17.scpier.data.content.ContentNode;
import org.jsoup.nodes.Element;

public abstract class ElementScraper {

    protected ElementScraper(String source) {
        this.source = source;
    }

    public abstract ContentNode<?> scrapElement(Element element) ;

    protected String source;

    protected String capitalizeText(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}

