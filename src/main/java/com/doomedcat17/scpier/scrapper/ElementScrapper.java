package com.doomedcat17.scpier.scrapper;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import org.jsoup.nodes.Element;

public abstract class ElementScrapper {

    protected ElementScrapper(String source) {
        this.source = source;
    }

    public abstract ContentNode<?> scrapElement(Element element) throws ElementScrapperException;

    protected String source;

    protected String capitalizeText(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}

