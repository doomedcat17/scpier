package com.doomedcat17.scpier.page.html.document.provider;

import com.doomedcat17.scpier.page.PageContent;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DefaultWikiPageProvider implements WikiPageProvider {

    public PageContent getWebpageContent(String url) throws IOException {
        Connection conn = Jsoup.connect(url);
        Document webpageContent = conn.get();
        PageContent pageContent = new PageContent();
        pageContent.setContent(webpageContent.getElementsByTag("body").first());
        pageContent.setSourceUrl(url);

        return pageContent;
    }

}
