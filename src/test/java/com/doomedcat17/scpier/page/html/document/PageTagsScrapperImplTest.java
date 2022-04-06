package com.doomedcat17.scpier.page.html.document;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapper;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapperImpl;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageTagsScrapperImplTest {

    private final PageTagsScrapper pageTagsScrapper = new PageTagsScrapperImpl();

    @Test
    void shouldScrapTags1() {
        //given
        Document document = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/document/sampleScpWithTags1.html");
        //when
        Optional<List<String>> foundTags = pageTagsScrapper.scrapPageTags(document);
        //then
        assertTrue(foundTags.isPresent());
        assertIterableEquals(List.of("adult",
                "biological", "cognitohazard",
                "compulsion", "euclid",
                "featured", "gustatory",
                "mind-affecting", "redirect",
                "scp", "sexual"),
                foundTags.get());
    }

    @Test
    void shouldScrapTags2() {
        //given
        Document document = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/document/sampleScpWithTags2.html");
        //when
        Optional<List<String>> foundTags = pageTagsScrapper.scrapPageTags(document);
        //then
        assertTrue(foundTags.isPresent());
        assertIterableEquals(List.of("4000",
                "animal", "avian",
                "_cc", "dinosaurian",
                "intangible", "keter",
                "ritual", "sapient",
                "scp", "sentient",
                "stone", "swarm"),
                foundTags.get());
    }

}