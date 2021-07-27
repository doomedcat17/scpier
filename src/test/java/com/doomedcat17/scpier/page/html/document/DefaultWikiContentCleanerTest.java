package com.doomedcat17.scpier.page.html.document;

import com.doomedcat17.scpier.page.html.document.cleaner.DefaultWikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.cleaner.WikiContentCleaner;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultWikiContentCleanerTest {

    private final WikiContentCleaner wikiContentCleaner = new DefaultWikiContentCleaner();

    private final Element testData = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/document/cleaner/page-contents.html");



    @Test
    void shouldClearSimpleDocument() {
        //given
        Element content = testData.getElementById("shouldCleanSimpleDocument");
        //when
        wikiContentCleaner.removeTrash(content);
        //then
        assertEquals(15, content.childrenSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearSimpleDocument3() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument3");
        //when
        wikiContentCleaner.removeTrash(content);
        //then
        assertEquals(109, content.childrenSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }


    @Test
    void shouldClearSimpleDocument5() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument5");
        //when
        wikiContentCleaner.removeTrash(content);
        //then
        assertEquals(12, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearSimpleDocument6() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument6");
        //when
        wikiContentCleaner.removeTrash(content);
        //then
        assertEquals(4, content.childNodeSize());
    }

    @Test
    void shouldClearSimpleDocument7() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument7");
        //when
        wikiContentCleaner.removeTrash(content);
        //then
        assertEquals(4, content.childNodeSize());
    }

    @Test
    void shouldClearSimpleDocument8() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument8");
        //when
        wikiContentCleaner.removeTrash(content);
        //then
        assertEquals(6, content.childNodeSize());
    }

    @Test
    void shouldClearAndUnpackSimpleDocument() {
        //given
        Element content = testData.getElementById("shouldClearAndUnpackSimpleDocument");
        //when
        wikiContentCleaner.clearContentAndUnpackBlocks(content);
        //then
        assertEquals(13, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearAndUnpackSimpleDocument2() {
        //given
        Element content = testData.getElementById("shouldClearAndUnpackSimpleDocument2");
        //when
        wikiContentCleaner.clearContentAndUnpackBlocks(content);
        //then
        assertEquals(12, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearAndUnpackSimpleDocument3() {
        //given
        Element content = testData.getElementById("shouldClearAndUnpackSimpleDocument3");
        //when
        wikiContentCleaner.clearContentAndUnpackBlocks(content);
        //then
        assertEquals(4, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty() && !element.is("img")));
    }

    @Test
    void shouldClearAndUnpackSimpleDocument4() {
        //given
        Element content = testData.getElementById("shouldClearAndUnpackSimpleDocument4");
        //when
        wikiContentCleaner.clearContentAndUnpackBlocks(content);
        //then
        assertEquals(8, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty() && !element.is("img")));
    }
    @Test
    void shouldClearAndUnpackSimpleDocument5() {
        //given
        Element content = testData.getElementById("shouldClearAndUnpackSimpleDocument5");
        //when
        wikiContentCleaner.clearContentAndUnpackBlocks(content);
        //then
        assertEquals(4, content.childNodeSize());
    }



}