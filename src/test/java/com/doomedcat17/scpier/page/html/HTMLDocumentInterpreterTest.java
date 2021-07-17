package com.doomedcat17.scpier.page.html;

import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.*;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTMLDocumentInterpreterTest {

    private final HTMLDocumentInterpreter htmlDocumentInterpreter = new HTMLDocumentInterpreter(
            new HTMLDocumentContentCleanerImpl(),
            new HTMLRedirectionHandler(new DefaultHTMLDocumentProvider()),
            new PageTagsScrapperImpl()
    );



    @Test
    void shouldClearContent() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-035.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(31, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-194.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(7, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv2() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-285.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(34, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv3() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-1496.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(6, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv4() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-2117.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(38, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-597.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(36, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent2() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-1004.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(14, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent3() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-2182.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(23, pageContent.getContent().childNodeSize());
    }


}