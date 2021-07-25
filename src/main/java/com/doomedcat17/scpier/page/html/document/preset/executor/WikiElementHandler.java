package com.doomedcat17.scpier.page.html.document.preset.executor;

import com.doomedcat17.scpier.page.html.document.preset.element.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

public class WikiElementHandler {


    public static void handleElement(WikiElement element, DomNode htmlElement) throws IOException {
        String selector = element.getSelector();
        if (element instanceof ButtonWikiElement) {
            HtmlAnchor button = htmlElement.querySelector(selector);
            button.click();
        } else if (element instanceof InputWikiElement) {
            InputWikiElement inputWikiElement = (InputWikiElement) element;
            HtmlInput input = htmlElement.querySelector(selector);
            input.setValueAttribute(inputWikiElement.getInputValue());
        } else if (element instanceof FormWikiElement) {
            FormWikiElement formWikiElement = (FormWikiElement) element;
            HtmlForm form = htmlElement.querySelector(selector);
            handleForm(formWikiElement, form);
        } else if (element instanceof CheckBoxWikiElement) {
            HtmlCheckBoxInput checkBoxInput = htmlElement.querySelector(selector);
            checkBoxInput.click();
        } else if (element instanceof RadioWikiElement) {
            HtmlRadioButtonInput radioInput = htmlElement.querySelector(selector);
            radioInput.click();
        }

    }

    private static void handleForm(FormWikiElement formWikiElement, HtmlForm form) throws IOException {
        for(WikiElement wikiElement: formWikiElement.getWikiElements()) {
            handleElement(wikiElement, form);
        }
        String selector = formWikiElement.getSubmitSelector();
        if (!selector.isBlank()) {
            HtmlButton submit = form.querySelector(selector);
            submit.click();
        }
    }
}
