package com.doomedcat17.scpier.page.html.document.preset.element;


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

    protected int jsRuntime = 0;

    public WikiElement(String selector, WikiElementType elementType, int jsRuntime) {
        this.selector = selector;
        this.elementType = elementType;
        this.jsRuntime = jsRuntime;
    }

    public String getSelector() {
        return selector;
    }

    public WikiElementType getElementType() {
        return elementType;
    }

    public WikiElement() {
    }

    public int getJsRuntime() {
        return jsRuntime;
    }
}
