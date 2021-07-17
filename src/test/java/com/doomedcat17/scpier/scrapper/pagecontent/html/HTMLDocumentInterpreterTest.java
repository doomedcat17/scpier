package com.doomedcat17.scpier.scrapper.pagecontent.html;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.doomedcat17.scpier.pagecontent.html.document.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTMLDocumentInterpreterTest {

    private final HTMLDocumentInterpreter htmlDocumentInterpreter = new HTMLDocumentInterpreter(
            new DocumentContentCleanerImpl(),
            new HTMLRedirectionHandler(new DefaultHTMLDocumentProvider()),
            new PageTagsScrapperImpl()
    );



    @Test
    void shouldClearContent() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/testdata/sample_scps/scp-035.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(31, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/testdata/sample_scps/scp-194.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(7, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv2() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/testdata/sample_scps/scp-285.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(34, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv3() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/testdata/sample_scps/scp-1496.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(6, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv4() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/testdata/sample_scps/scp-2117.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(38, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/testdata/sample_scps/scp-597.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(36, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent2() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/testdata/sample_scps/scp-1004.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(14, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent3() {
        //given
        PageContent pageContent = TestDataProvider.getPageContent("src/test/resources/html/testdata/sample_scps/scp-2182.html");
        //when
        htmlDocumentInterpreter.mapContent(pageContent);
        //then
        assertEquals(23, pageContent.getContent().childNodeSize());
    }


}