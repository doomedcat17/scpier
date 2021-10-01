package com.doomedcat17.scpier.page.html.document.provider;

import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.WebClientProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ScriptedWikiPageProvider implements WikiPageProvider {

    @Override
    public WikiContent getWebpageContent(String url) throws IOException {
        WebClient webClient = WebClientProvider.getWebClient();
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(500);
        String htmlContent = page.executeJavaScript("document.body.parentNode.outerHTML")
                .getJavaScriptResult()
                .toString();
        Document webpageContent = Jsoup.parse(htmlContent);
        WikiContent wikiContent = new WikiContent();
        wikiContent.setContent(webpageContent.getElementsByTag("body").first());
        wikiContent.setOriginalSourceUrl(url);
        return wikiContent;
    }


}
