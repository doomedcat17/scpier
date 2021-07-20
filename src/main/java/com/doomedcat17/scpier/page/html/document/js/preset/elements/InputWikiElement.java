package com.doomedcat17.scpier.page.html.document.js.preset.elements;

public class InputWikiElement extends WikiElement {

    private String inputValue;

    public InputWikiElement(String selector, String inputValue) {
        super(selector, WikiElementType.INPUT);
        this.inputValue = inputValue;
    }

    public String getInputValue() {
        return inputValue;
    }

    public InputWikiElement() {
        this.elementType = WikiElementType.INPUT;
    }
}
