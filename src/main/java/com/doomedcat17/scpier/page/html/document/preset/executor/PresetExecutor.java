package com.doomedcat17.scpier.page.html.document.preset.executor;

import com.doomedcat17.scpier.exception.PresetExecutorException;
import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.WebClientProvider;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.element.WikiElement;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PresetExecutor {


    public PageContent execute(Preset preset, String src) throws PresetExecutorException {
        try {
            WebClient webClient = WebClientProvider.getWebClient();
            HtmlPage page = webClient.getPage(src);
            for (WikiElement element : preset.getWikiElements()) {
                WikiElementHandler.handleElement(element, page);
            }
            webClient.waitForBackgroundJavaScript(preset.getJsRuntime());
            String html = page.executeJavaScript("document.body.parentNode.outerHTML")
                    .getJavaScriptResult()
                    .toString();
            Document document = Jsoup.parse(html);
            PageContent pageContent = new PageContent();
            pageContent.setContent(document.getElementsByTag("body").first());
            return pageContent;
        } catch (IOException e) {
            throw new PresetExecutorException(e.getMessage());
        }
    }

}
