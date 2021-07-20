package com.doomedcat17.scpier.page.html.document.js;

import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.HTMLDocumentProvider;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Level;

public class ScriptedHTMLDocumentProvider implements HTMLDocumentProvider {

    private final WebClient webClient;

    @Override
    public PageContent getWebpageContent(String url) throws IOException {
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(200);
        String htmlContent = page.executeJavaScript("document.body.parentNode.outerHTML")
                .getJavaScriptResult()
                .toString();
        Document webpageContent = Jsoup.parse(htmlContent);
        PageContent pageContent = new PageContent();
        pageContent.setContent(webpageContent.getElementsByTag("body").first());
        pageContent.setSourceUrl(url);
        return pageContent;
    }

    public ScriptedHTMLDocumentProvider() {
        webClient = new WebClient(BrowserVersion.CHROME);
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
    }
}
