package com.doomedcat17.scpier.page.html.document.js.preset.element;

import java.util.List;

public class FormWikiElement extends WikiElement {

    private List<WikiElement> wikiElements;

    private String submitSelector;

    public FormWikiElement(String selector, List<WikiElement> wikiElements, String submitSelector) {
        super(selector, WikiElementType.FORM);
        this.wikiElements = wikiElements;
        this.submitSelector = submitSelector;
    }

    public List<WikiElement> getWikiElements() {
        return wikiElements;
    }

    public String getSubmitSelector() {
        return submitSelector;
    }

    public FormWikiElement() {
        this.elementType = WikiElementType.FORM;
    }
}
