package com.doomedcat17.scpier.page_content.html.document;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PageTagsScrapperImpl implements PageTagsScrapper {
    @Override
    public Optional<List<String>> scrapPageTags(Document contentDocument) {
        List<String> tagNames = new ArrayList<>();
        Element tagsElement = contentDocument.selectFirst(".page-tags");
        if (tagsElement == null) return Optional.empty();
        List<Element> tagElements = tagsElement.children();
        if (tagElements.size() == 1) tagElements = tagElements.get(0).children();
        for (Element element: tagElements) {
            tagNames.add(element.text().trim());
        }
        if (tagNames.isEmpty()) return Optional.empty();
        else return Optional.of(tagNames);
    }
}
