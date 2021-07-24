package com.doomedcat17.scpier.page.html.document.js.preset.executor;

import com.doomedcat17.scpier.exception.PresetExecutorException;
import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.js.WebClientProvider;
import com.doomedcat17.scpier.page.html.document.js.preset.Preset;
import com.doomedcat17.scpier.page.html.document.js.preset.element.ButtonWikiElement;
import com.doomedcat17.scpier.page.html.document.js.preset.element.FormWikiElement;
import com.doomedcat17.scpier.page.html.document.js.preset.element.InputWikiElement;
import com.doomedcat17.scpier.page.html.document.js.preset.element.WikiElement;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PresetExecutor {


    public PageContent execute(Preset preset, String src) throws PresetExecutorException {
        try {
            WebClient webClient = WebClientProvider.getWebClient();
            HtmlPage page = webClient.getPage(src);
            for (WikiElement element : preset.getWikiElements()) {
                handleElement(element, page);
            }
            webClient.waitForBackgroundJavaScript(preset.getJsTime());
            String html = page.executeJavaScript("document.body.parentNode.outerHTML")
                    .getJavaScriptResult()
                    .toString();
            Document document = Jsoup.parse(html);
            PageContent pageContent = new PageContent();
            pageContent.setContent(document.getElementsByTag("body").first());
            return pageContent;
        } catch (IOException e) {
            e.printStackTrace();
            throw new PresetExecutorException(e.getMessage());
        }
    }

    private void handleElement(WikiElement element, DomNode htmlElement) throws IOException {
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
        }

    }

    private void handleForm(FormWikiElement formWikiElement, HtmlForm form) {
        for(WikiElement wikiElement: formWikiElement.getWikiElements()) {

        }
    }

}
