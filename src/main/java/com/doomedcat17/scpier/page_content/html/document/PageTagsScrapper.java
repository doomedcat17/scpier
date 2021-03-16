package com.doomedcat17.scpier.page_content.html.document;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Optional;

public interface PageTagsScrapper {

    Optional<List<String>> scrapPageTags(Document contentDocument);
}
