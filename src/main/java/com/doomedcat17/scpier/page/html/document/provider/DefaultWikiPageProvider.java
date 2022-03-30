package com.doomedcat17.scpier.page.html.document.provider;

import com.doomedcat17.scpier.page.WikiContent;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DefaultWikiPageProvider implements WikiPageProvider {

    private final WebClient webClient;

    @Override
    public WikiContent getWebpageContent(String url) throws IOException {
        try (webClient) {
            HtmlPage page = webClient.getPage(url);
            page.getEnclosingWindow().getJobManager().waitForJobs(500);
            String htmlContent = page.executeJavaScript("document.body.parentNode.outerHTML")
                    .getJavaScriptResult()
                    .toString();
            Document webpageContent = Jsoup.parse(htmlContent);
            webClient.close();
            WikiContent wikiContent = new WikiContent();
            wikiContent.setContent(webpageContent.selectFirst("body"));
            wikiContent.setOriginalSourceUrl(url);
            return wikiContent;
        }
    }

    public DefaultWikiPageProvider(WebClient webClient) {
        this.webClient = webClient;
    }
}
