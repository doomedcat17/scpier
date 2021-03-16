package com.doomedcat17.scpier.page_content.html.document;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HTMLDocumentProvider {

    public Document getWebpageContent(String url) throws IOException {
        Connection conn = Jsoup.connect(url);
        return conn.get();
    }
}
