package com.doomedcat17.scpier.page.html.document.author;
import com.doomedcat17.scpier.page.html.document.WebClientProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class AuthorScraper {

    public String scrap(String source) throws IOException {
        try {
            Element element = getAuthorElement(source);
            return element.selectFirst(".printuser a:last-child").text();
        } catch (NullPointerException e) {
            return "Unknown";
        }
    }


    protected Element getAuthorElement(String source) throws IOException {
        WebClient webClient = WebClientProvider.getWebClient();
        HtmlPage page = webClient.getPage(source);
        HtmlAnchor historyAnchor = (HtmlAnchor) page.getElementById("history-button");
        historyAnchor.click();
        webClient.waitForBackgroundJavaScript(1500);
        HtmlSelect select = page.querySelector("#h-perpage");
        HtmlOption option = select.getOptionByValue("200");
        select.setSelectedAttribute(option, true);
        HtmlInput refreshButton = page.querySelector(".buttons :first-child");
        refreshButton.click();
        webClient.waitForBackgroundJavaScript(1500);
        HtmlAnchor nextButton = page.querySelector(".target:last-child > a");
        while (nextButton != null) {
            nextButton.click();
            webClient.waitForBackgroundJavaScript(2000);
            nextButton = page.querySelector(".target:last-child > a");
        }
        HtmlElement htmlElement = page.querySelector(".page-history tbody  tr:last-child");
        return Jsoup.parse(htmlElement.asXml());
    }
}
