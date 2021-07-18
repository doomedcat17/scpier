package com.doomedcat17.scpier.page.html.document;

import com.doomedcat17.scpier.page.PageContent;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DefaultHTMLDocumentProvider implements HTMLDocumentProvider {

    public PageContent getWebpageContent(String url) throws IOException {
        Connection conn = Jsoup.connect(url);
        Document webpageContent = conn.get();
        PageContent pageContent = new PageContent();
        pageContent.setContent(webpageContent.getElementsByTag("body").first());
        pageContent.setSourceUrl(url);
        return pageContent;
    }

}
