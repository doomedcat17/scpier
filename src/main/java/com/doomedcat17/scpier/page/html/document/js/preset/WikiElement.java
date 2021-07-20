package com.doomedcat17.scpier.page.html.document.js.preset;

public class WikiElement {

    private final String selector;

    private final WikiElementType wikiElementType;

    public WikiElement(String selector, WikiElementType wikiElementType) {
        this.selector = selector;
        this.wikiElementType = wikiElementType;
    }

    public String getSelector() {
        return selector;
    }

    public WikiElementType getWikiElementType() {
        return wikiElementType;
    }
}
