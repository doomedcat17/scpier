package com.doomedcat17.scpier.page.html.document.js.preset;

import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.js.preset.elements.ButtonWikiElement;
import com.doomedcat17.scpier.page.html.document.js.preset.elements.FormWikiElement;
import com.doomedcat17.scpier.page.html.document.js.preset.elements.InputWikiElement;
import com.doomedcat17.scpier.page.html.document.js.preset.elements.WikiElement;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Level;

public class PresetExecutor {

    private final WebClient JS_RUNNER;

    public PageContent execute(ScpInputPreset scpInputPreset, String src) throws IOException {
        HtmlPage page = JS_RUNNER.getPage(src);
        for(WikiElement element: scpInputPreset.getWikiElements()) {
            String selector = element.getSelector();
            if (element instanceof ButtonWikiElement) {
                HtmlAnchor button = page.querySelector(selector);
                button.click();
            } else if (element instanceof InputWikiElement) {
                InputWikiElement inputWikiElement = (InputWikiElement) element;
                HtmlInput input = page.querySelector(selector);
                input.setValueAttribute(inputWikiElement.getInputValue());
            } else if (element instanceof FormWikiElement) {
                FormWikiElement formWikiElement = (FormWikiElement) element;
                HtmlForm form = page.querySelector(selector);
                //TODO skoncz to kurde
            }
            JS_RUNNER.waitForBackgroundJavaScript(100);
        }
        String html = page.executeJavaScript("document.body.parentNode.outerHTML")
                .getJavaScriptResult()
                .toString();
        Document document = Jsoup.parse(html);
        PageContent pageContent = new PageContent();
        pageContent.setContent(document.getElementsByTag("body").first());
        return pageContent;
    }

    public PresetExecutor() {
        this.JS_RUNNER = new WebClient(BrowserVersion.CHROME);
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        JS_RUNNER.getOptions().setJavaScriptEnabled(true);
        JS_RUNNER.getOptions().setThrowExceptionOnScriptError(false);
    }
}
