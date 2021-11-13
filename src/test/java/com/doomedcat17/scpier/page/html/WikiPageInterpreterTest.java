package com.doomedcat17.scpier.page.html;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.author.AuthorScraper;
import com.doomedcat17.scpier.page.html.document.cleaner.DefaultWikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.interpreter.WikiPageInterpreter;
import com.doomedcat17.scpier.page.html.document.provider.DefaultWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.redirection.WikiRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapperImpl;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WikiPageInterpreterTest {

    private final WikiPageInterpreter wikiPageInterpreter = new WikiPageInterpreter(
            new DefaultWikiContentCleaner(ResourcesProvider.getRemovalDefinitions()),
            new WikiRedirectionHandler(new DefaultWikiPageProvider(), ResourcesProvider.getRedirectionDefinitions()),
            new PageTagsScrapperImpl(),
            new AuthorScraper()
    );

    @BeforeAll
    static void init() throws IOException, URISyntaxException {
        ResourcesProvider.initResources();
    }


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