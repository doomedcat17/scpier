package com.doomedcat17.scpier.page.html.document.preset.executor;

import com.doomedcat17.scpier.exception.page.html.document.preset.PresetExecutorException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.element.WikiElement;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PresetExecutor {

    public static WikiContent execute(WebClient webClient, Preset preset, String src) throws PresetExecutorException {
        try {
            HtmlPage page = webClient.getPage(src);
            for (WikiElement element : preset.getWikiElements()) {
                WikiElementHandler.handleElement(element, page);
                webClient.waitForBackgroundJavaScript(element.getJsRuntime());
            }
            webClient.waitForBackgroundJavaScript(preset.getJsRuntime());
            String html = page.executeJavaScript("document.body.parentNode.outerHTML")
                    .getJavaScriptResult()
                    .toString();
            Document document = Jsoup.parse(html);
            WikiContent wikiContent = new WikiContent();
            wikiContent.setContent(document.getElementsByTag("body").first());
            return wikiContent;
        } catch (IOException e) {
            throw new PresetExecutorException(e);
        }
    }

}
