package com.doomedcat17.scpier.page.html.document.js.preset.elements;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "elementType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value=WikiElement.class, name = "SIMPLE"),
        @JsonSubTypes.Type(value=InputWikiElement.class, name = "INPUT"),
        @JsonSubTypes.Type(value=FormWikiElement.class, name = "FORM"),
        @JsonSubTypes.Type(value=ButtonWikiElement.class, name = "BUTTON"),
        @JsonSubTypes.Type(value=RadioWikiElement.class, name = "RADIO"),
        @JsonSubTypes.Type(value=CheckBoxWikiElement.class, name = "CHECKBOX")
})
public class WikiElement {

    protected String selector;

    protected WikiElementType elementType;

    public WikiElement(String selector, WikiElementType elementType) {
        this.selector = selector;
        this.elementType = elementType;
    }

    public String getSelector() {
        return selector;
    }

    public WikiElementType getElementType() {
        return elementType;
    }

    public WikiElement() {
    }
}
