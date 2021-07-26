package com.doomedcat17.scpier.page.html.document.preset.element;

public class InputWikiElement extends WikiElement {

    private String inputValue;

    public InputWikiElement(String selector, String inputValue, int jsRuntime) {
        super(selector, WikiElementType.INPUT, jsRuntime);
        this.inputValue = inputValue;
    }

    public String getInputValue() {
        return inputValue;
    }

    public InputWikiElement() {
        this.elementType = WikiElementType.INPUT;
    }
}
