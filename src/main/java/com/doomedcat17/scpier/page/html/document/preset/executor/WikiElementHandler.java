package com.doomedcat17.scpier.page.html.document.preset.executor;

import com.doomedcat17.scpier.page.html.document.preset.element.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

public class WikiElementHandler {


    public static void handleElement(WikiElement element, DomNode htmlElement) throws IOException {
        String selector = element.getSelector();
        DomElement domElement = htmlElement.querySelector(selector);
        if (domElement != null) {
            if (element instanceof ButtonWikiElement) {
                domElement.click();
            } else if (element instanceof InputWikiElement) {
                InputWikiElement inputWikiElement = (InputWikiElement) element;
                HtmlInput input = (HtmlInput) domElement;
                input.setValueAttribute(inputWikiElement.getInputValue());
            } else if (element instanceof CheckBoxWikiElement) {
                HtmlCheckBoxInput checkBoxInput = (HtmlCheckBoxInput) domElement;
                checkBoxInput.click();
            } else if (element instanceof RadioWikiElement) {
                HtmlRadioButtonInput radioInput = (HtmlRadioButtonInput) domElement;
                radioInput.click();
            }
        } else throw new IllegalArgumentException("WikiElement not found!");

    }
}
