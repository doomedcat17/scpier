package com.doomedcat17.scpier.page.html.document;

import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class HTMLRedirectionHandlerTest {

    @Mock
    private HTMLDocumentProvider htmlDocumentProvider;

    private HTMLRedirectionHandler htmlRedirectionHandler;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        htmlRedirectionHandler = new HTMLRedirectionHandler(htmlDocumentProvider);
    }

    @Test
    void shouldFindRedirection() {
        //given
        Element content = TestDataProvider
                .getSampleElements("src/test/resources/html/test_data/document/sampleScpWithRedirection.html")
                .getElementById("page-content");
        //when
        Optional<Element> foundRedirection =
                htmlRedirectionHandler.checkForRedirection(content);
        //then
        assertTrue(foundRedirection.isPresent());
    }

    @Test
    void shouldNotFindRedirection() {
        //given
        Element content = TestDataProvider
                .getSampleElements("src/test/resources/html/test_data/document/sampleScpWithoutRedirection.html")
                .getElementById("page-content");
        //when
        Optional<Element> foundRedirection =
                htmlRedirectionHandler.checkForRedirection(content);
        //then
        assertTrue(foundRedirection.isEmpty());
    }

    @Test
    void shouldRedirect() throws IOException {
        //given
        Document document = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/document/redirectedScp.html");
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/adult:scp-597/noredirect/true"))
                .thenReturn(new PageContent(document.getElementsByTag("body").first()));
        Element content = TestDataProvider
                .getSampleElements("src/test/resources/html/test_data/document/sampleScpWithRedirection.html")
                .getElementById("page-content");

        //when
        Optional<Element> foundRedirection =
                htmlRedirectionHandler.checkForRedirection(content);

        if (foundRedirection.isEmpty()) fail();
        Element redirectionElement = foundRedirection.get();
        Element redirectedContent =
                htmlRedirectionHandler.getRedirectionContent(redirectionElement, "http://www.scpwiki.com/scp-597");
        //then
        assertEquals(document.getElementById("page-content"), redirectedContent);
    }

}