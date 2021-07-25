package com.doomedcat17.scpier.page.html.document.js;

import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ScriptedWikiPageProvider implements WikiPageProvider {

    @Override
    public PageContent getWebpageContent(String url) throws IOException {
        WebClient webClient = WebClientProvider.getWebClient();
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(300);
        String htmlContent = page.executeJavaScript("document.body.parentNode.outerHTML")
                .getJavaScriptResult()
                .toString();
        Document webpageContent = Jsoup.parse(htmlContent);
        PageContent pageContent = new PageContent();
        pageContent.setContent(webpageContent.getElementsByTag("body").first());
        pageContent.setSourceUrl(url);
        return pageContent;
    }


}
