package com.doomedcat17.scpier.page.html.document;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PageTagsScrapperImpl implements PageTagsScrapper {
    @Override
    public Optional<List<String>> scrapPageTags(Element contentElement) {
        List<String> tagNames = new ArrayList<>();
        Element tagsElement = contentElement.selectFirst(".page-tags");
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
