package com.doomedcat17.scpier.page.html.document;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Optional;

public interface PageTagsScrapper {

    Optional<List<String>> scrapPageTags(Element contentElement);
}
