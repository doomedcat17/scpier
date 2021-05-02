package com.doomedcat17.scpier.pagecontent.html.document;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.logging.Level;

public class HTMLDocumentProviderFactory {

    public static HTMLDocumentProvider getHTMLDocumentProvider(String name, SCPBranch branch, SCPTranslation translation) {
        if (branch.equals(SCPBranch.ENGLISH)) {
            switch (name.toLowerCase()) {
                case "3211":
                    return url -> {
                        WebClient webClient = new WebClient(BrowserVersion.CHROME);
                        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
                        webClient.getOptions().setJavaScriptEnabled(true);
                        webClient.getOptions().setThrowExceptionOnScriptError(false);
                        DefaultHTMLDocumentProvider defaultHTMLDocumentProvider = new DefaultHTMLDocumentProvider();
                        PageContent originalContent = defaultHTMLDocumentProvider
                                .getWebpageContent(url);
                        String translationUrl = translation.url;
                        String contentId = originalContent.getContent()
                                .selectFirst("#page-content .html-block-iframe").attr("src")
                                .replaceFirst("/scp-3211/html/", "").trim();
                        if (translation.equals(SCPTranslation.ORIGINAL)) translationUrl = branch.url;
                        String source = translationUrl
                                .replaceFirst("wikidot", "wdfiles").replaceFirst("www.", "")
                                +"local--html/scp-3211/"+contentId+"/"
                                +translationUrl.replace("http://", "");
                        if (translation.equals(SCPTranslation.ORIGINAL)) source = source.replace(".wikidot", "");
                        HtmlPage page = webClient.getPage(source);
                        HtmlAnchor anchor = (HtmlAnchor) page.getElementById("proceed");
                        page = anchor.click();
                        webClient.waitForBackgroundJavaScript(1200);
                        String htmlContent = page.executeJavaScript("document.body.parentNode.outerHTML")
                                .getJavaScriptResult()
                                .toString();
                        Document document = Jsoup.parse(htmlContent);
                        Element scpContent = document.getElementById("anomaly").clone();
                        Element documentBody = document.getElementsByTag("body").first();
                        documentBody.empty();
                        Element pageContentElement = new Element("div");
                        pageContentElement.attr("id", "page-content");
                        pageContentElement.appendChild(scpContent);
                        documentBody.appendChild(pageContentElement);
                        Element tagsElement = originalContent
                                .getContent()
                                .getElementsByClass("page-tags").first().clone();
                        Element pageTitleElement = originalContent.getContent().getElementById("page-title").clone();
                        documentBody.appendChild(tagsElement);
                        documentBody.appendChild(pageTitleElement);
                        PageContent pageContent = new PageContent();
                        pageContent.setContent(documentBody);
                        pageContent.setSourceUrl(url);
                        return pageContent;
                    };
                default:
                    return new DefaultHTMLDocumentProvider();
            }
        }
        return new DefaultHTMLDocumentProvider();
    }
}
