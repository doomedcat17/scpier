package com.doomedcat17.scpier.page.html.document;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.doomedcat17.scpier.page.html.document.redirection.WikiRedirectionHandler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class WikiRedirectionHandlerTest {

    @Mock
    private WikiPageProvider wikiPageProvider;

    private WikiRedirectionHandler wikiRedirectionHandler;


    @BeforeAll
    static void initResources() throws IOException, URISyntaxException {
        ResourcesProvider.initResources();
    }

    @BeforeEach
    void init() throws IOException, URISyntaxException {
        MockitoAnnotations.initMocks(this);
        ResourcesProvider.initResources();
        wikiRedirectionHandler = new WikiRedirectionHandler(wikiPageProvider, ResourcesProvider.getRedirectionDefinitions());
    }

    @Test
    void shouldFindRedirection() {
        //given
        Element content = TestDataProvider
                .getSampleElement("src/test/resources/html/test_data/document/sampleScpWithRedirection.html")
                .getElementById("page-content");
        //when
        Optional<Element> foundRedirection =
                wikiRedirectionHandler.provideRedirectedContent(content);
        //then
        assertTrue(foundRedirection.isPresent());
    }

    @Test
    void shouldNotFindRedirection() {
        //given
        Element content = TestDataProvider
                .getSampleElement("src/test/resources/html/test_data/document/sampleScpWithoutRedirection.html")
                .getElementById("page-content");
        //when
        Optional<Element> foundRedirection =
                wikiRedirectionHandler.provideRedirectedContent(content);
        //then
        assertTrue(foundRedirection.isEmpty());
    }

    @Test
    void shouldRedirect() throws IOException {
        //given
        Document document = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/document/redirectedScp.html");
        Mockito.when(wikiPageProvider.getWebpageContentWithoutRunningJs("http://www.scpwiki.com/adult:scp-597/noredirect/true"))
                .thenReturn(new WikiContent(document.getElementsByTag("body").first()));
        Element content = TestDataProvider
                .getSampleElement("src/test/resources/html/test_data/document/sampleScpWithRedirection.html")
                .getElementById("page-content");

        //when
        Optional<Element> foundRedirection =
                wikiRedirectionHandler.provideRedirectedContent(content);

        if (foundRedirection.isEmpty()) fail();
        Element redirectionElement = foundRedirection.get();
        Element redirectedContent =
                wikiRedirectionHandler.getRedirectionContent(redirectionElement, "http://www.scpwiki.com/scp-597").getElementById("page-content");
        //then
        assertEquals(document.getElementById("page-content"), redirectedContent);
    }

}