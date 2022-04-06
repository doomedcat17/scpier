package com.doomedcat17.scpier.page.html.document;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.page.html.document.cleaner.DefaultWikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.cleaner.WikiContentCleaner;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultWikiContentCleanerTest {

    private final WikiContentCleaner wikiContentCleaner = new DefaultWikiContentCleaner(ResourcesProvider.getRemovalDefinitions());

    private final Element testData = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/document/cleaner/page-contents.html");

    @BeforeAll
    static void init() throws IOException, URISyntaxException {
        ResourcesProvider.initResources();
    }


    @Test
    void shouldClearSimpleDocument() {
        //given
        Element content = testData.getElementById("shouldCleanSimpleDocument");
        //when
        wikiContentCleaner.removeTrashNodes(content);
        //then
        assertEquals(15, content.childrenSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearSimpleDocument3() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument3");
        //when
        wikiContentCleaner.removeTrashNodes(content);
        //then
        assertEquals(109, content.childrenSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }


    @Test
    void shouldClearSimpleDocument5() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument5");
        //when
        wikiContentCleaner.removeTrashNodes(content);
        //then
        assertEquals(12, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearSimpleDocument6() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument6");
        //when
        wikiContentCleaner.removeTrashNodes(content);
        //then
        assertEquals(4, content.childNodeSize());
    }

    @Test
    void shouldClearSimpleDocument7() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument7");
        //when
        wikiContentCleaner.removeTrashNodes(content);
        //then
        assertEquals(4, content.childNodeSize());
    }

    @Test
    void shouldClearSimpleDocument8() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument8");
        //when
        wikiContentCleaner.removeTrashNodes(content);
        //then
        assertEquals(6, content.childNodeSize());
    }




}