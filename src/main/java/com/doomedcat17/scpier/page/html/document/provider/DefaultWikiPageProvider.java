package com.doomedcat17.scpier.page.html.document.provider;

import com.doomedcat17.scpier.page.WikiContent;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DefaultWikiPageProvider implements WikiPageProvider {

    public WikiContent getWebpageContent(String url) throws IOException {
        Connection conn = Jsoup.connect(url);
        Document webpageContent = conn.get();
        WikiContent wikiContent = new WikiContent();
        wikiContent.setContent(webpageContent.getElementsByTag("body").first());
        wikiContent.setSourceUrl(url);
        return wikiContent;

    }

}
