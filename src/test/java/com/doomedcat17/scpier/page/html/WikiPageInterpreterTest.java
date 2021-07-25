package com.doomedcat17.scpier.page.html;

import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.cleaner.HTMLDocumentContentCleanerImpl;
import com.doomedcat17.scpier.page.html.document.interpreter.WikiPageInterpreter;
import com.doomedcat17.scpier.page.html.document.provider.DefaultWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.redirection.WikiRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapperImpl;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WikiPageInterpreterTest {

    private final WikiPageInterpreter wikiPageInterpreter = new WikiPageInterpreter(
            new HTMLDocumentContentCleanerImpl(),
            new WikiRedirectionHandler(new DefaultWikiPageProvider()),
            new PageTagsScrapperImpl()
    );



    @Test
    void shouldClearContent() {
        //given
        WikiContent wikiContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-035.html");
        //when
        wikiPageInterpreter.mapContent(wikiContent);
        //then
        assertEquals(31, wikiContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv() {
        //given
        WikiContent wikiContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-194.html");
        //when
        wikiPageInterpreter.mapContent(wikiContent);
        //then
        assertEquals(7, wikiContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv2() {
        //given
        WikiContent wikiContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-285.html");
        //when
        wikiPageInterpreter.mapContent(wikiContent);
        //then
        assertEquals(34, wikiContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv3() {
        //given
        WikiContent wikiContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-1496.html");
        //when
        wikiPageInterpreter.mapContent(wikiContent);
        //then
        assertEquals(6, wikiContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv4() {
        //given
        WikiContent wikiContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-2117.html");
        //when
        wikiPageInterpreter.mapContent(wikiContent);
        //then
        assertEquals(38, wikiContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent() {
        //given
        WikiContent wikiContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-597.html");
        //when
        wikiPageInterpreter.mapContent(wikiContent);
        //then
        assertEquals(36, wikiContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent2() {
        //given
        WikiContent wikiContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-1004.html");
        //when
        wikiPageInterpreter.mapContent(wikiContent);
        //then
        assertEquals(14, wikiContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent3() {
        //given
        WikiContent wikiContent = TestDataProvider.getPageContent("src/test/resources/html/test_data/sample_scps/scp-2182.html");
        //when
        wikiPageInterpreter.mapContent(wikiContent);
        //then
        assertEquals(23, wikiContent.getContent().childNodeSize());
    }


}