package com.doomedcat17.page_content.html.document;

import com.doomedcat17.scpapi.TestDataProvider;
import com.doomedcat17.scpier.page_content.html.document.HTMLDocumentProvider;
import com.doomedcat17.scpier.page_content.html.document.HTMLRedirectionHandler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.util.Optional;


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
                .thenReturn(document);
        Element content = TestDataProvider
                .getSampleElements("src/test/resources/html/test_data/document/sampleScpWithRedirection.html")
                .getElementById("page-content");
        Optional<Element> foundRedirection =
                htmlRedirectionHandler.checkForRedirection(content);
        if (foundRedirection.isEmpty()) fail();
        Element redirectionElement = foundRedirection.get();
        //when
        Element redirectedContent =
                htmlRedirectionHandler.getRedirectionContent(redirectionElement, "http://www.scpwiki.com/scp-597");
        //then
        assertEquals(document.getElementById("page-content"), redirectedContent);
    }

}