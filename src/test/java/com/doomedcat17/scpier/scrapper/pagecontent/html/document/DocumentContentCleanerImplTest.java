package com.doomedcat17.scpier.scrapper.pagecontent.html.document;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.pagecontent.html.document.DocumentContentCleaner;
import com.doomedcat17.scpier.pagecontent.html.document.DocumentContentCleanerImpl;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DocumentContentCleanerImplTest {

    private final DocumentContentCleaner documentContentCleaner = new DocumentContentCleanerImpl();

    private final Element testData = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/testdata/document/document_cleaner_test_data/page-contents.html");



    @Test
    void shouldClearSimpleDocument() {
        //given
        Element content = testData.getElementById("shouldCleanSimpleDocument");
        //when
        documentContentCleaner.removeTrash(content);
        //then
        assertEquals(15, content.childrenSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearSimpleDocument2() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument2");
        //when
        documentContentCleaner.removeTrash(content);
        //then
        assertEquals(12, content.childrenSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearSimpleDocument3() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument3");
        //when
        documentContentCleaner.removeTrash(content);
        //then
        assertEquals(109, content.childrenSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearSimpleDocument4() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument4");
        //when
        documentContentCleaner.removeTrash(content);
        //then
        assertEquals(29, content.childrenSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearSimpleDocument5() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument5");
        //when
        documentContentCleaner.removeTrash(content);
        //then
        assertEquals(12, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearSimpleDocument6() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument6");
        //when
        documentContentCleaner.removeTrash(content);
        //then
        assertEquals(4, content.childNodeSize());
    }

    @Test
    void shouldClearSimpleDocument7() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument7");
        //when
        documentContentCleaner.removeTrash(content);
        //then
        assertEquals(4, content.childNodeSize());
    }

    @Test
    void shouldClearSimpleDocument8() {
        //given
        Element content = testData.getElementById("shouldClearSimpleDocument8");
        //when
        documentContentCleaner.removeTrash(content);
        //then
        assertEquals(6, content.childNodeSize());
    }

    @Test
    void shouldClearAndUnpackSimpleDocument() {
        //given
        Element content = testData.getElementById("shouldClearAndUnpackSimpleDocument");
        //when
        documentContentCleaner.clearContentAndUnpackBlocks(content);
        //then
        assertEquals(13, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearAndUnpackSimpleDocument2() {
        //given
        Element content = testData.getElementById("shouldClearAndUnpackSimpleDocument2");
        //when
        documentContentCleaner.clearContentAndUnpackBlocks(content);
        //then
        assertEquals(12, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty()));
    }

    @Test
    void shouldClearAndUnpackSimpleDocument3() {
        //given
        Element content = testData.getElementById("shouldClearAndUnpackSimpleDocument3");
        //when
        documentContentCleaner.clearContentAndUnpackBlocks(content);
        //then
        assertEquals(4, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty() && !element.is("img")));
    }

    @Test
    void shouldClearAndUnpackSimpleDocument4() {
        //given
        Element content = testData.getElementById("shouldClearAndUnpackSimpleDocument4");
        //when
        documentContentCleaner.clearContentAndUnpackBlocks(content);
        //then
        assertEquals(8, content.childNodeSize());
        assertTrue(content.children().stream().noneMatch(element -> element.childNodes().isEmpty() && !element.is("img")));
    }
    @Test
    void shouldClearAndUnpackSimpleDocument5() {
        //given
        Element content = testData.getElementById("shouldClearAndUnpackSimpleDocument5");
        //when
        documentContentCleaner.clearContentAndUnpackBlocks(content);
        //then
        assertEquals(4, content.childNodeSize());
    }



}