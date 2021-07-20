package com.doomedcat17.scpier.page.html.document.js.preset;

import java.util.List;

public class FormWikiElement extends WikiElement {

    private final List<WikiElement> wikiElements;

    private final String submitSelector;

    public FormWikiElement(String selector, WikiElementType wikiElementType, List<WikiElement> wikiElements, String submitSelector) {
        super(selector, wikiElementType);
        this.wikiElements = wikiElements;
        this.submitSelector = submitSelector;
    }

    public List<WikiElement> getWikiElements() {
        return wikiElements;
    }

    public String getSubmitSelector() {
        return submitSelector;
    }
}
