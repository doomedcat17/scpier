package com.doomedcat17.scpier.page.html.document.js.preset;

public class InputWikiElement extends WikiElement {

    private final String inputValue;

    public InputWikiElement(String selector, WikiElementType wikiElementType, String inputValue) {
        super(selector, wikiElementType);
        this.inputValue = inputValue;
    }

    public String getInputValue() {
        return inputValue;
    }
}
