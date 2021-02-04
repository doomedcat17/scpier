package pl.doomedcat17.scpapi.domain.scp.http.html;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.html.HTMLDocumentInterpreter;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.PageContent;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HTMLDocumentInterpreterTest {

    private HTMLDocumentInterpreter htmlDocumentInterpreter = new HTMLDocumentInterpreter();

    private PageContent pageContent;

    @BeforeEach
    void init() {
        pageContent = new PageContent();
    }

    @Test
    void shouldClearContent() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-035.html");
        //when
        htmlDocumentInterpreter.mapDocument(scpDocument, pageContent);
        //then
        assertEquals(31, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-194.html");
        //when
        htmlDocumentInterpreter.mapDocument(scpDocument, pageContent);
        //then
        assertEquals(7, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv2() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-285.html");
        //when
        htmlDocumentInterpreter.mapDocument(scpDocument, pageContent);
        //then
        assertEquals(34, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv3() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-1496.html");
        //when
        htmlDocumentInterpreter.mapDocument(scpDocument, pageContent);
        //then
        assertEquals(6, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv4() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-2117.html");
        //when
        htmlDocumentInterpreter.mapDocument(scpDocument, pageContent);
        //then
        assertEquals(38, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-597.html");
        //when
        htmlDocumentInterpreter.mapDocument(scpDocument, pageContent);
        //then
        assertEquals(36, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent2() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-1004.html");
        //when
        htmlDocumentInterpreter.mapDocument(scpDocument, pageContent);
        //then
        assertEquals(14, pageContent.getContent().childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent3() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-2182.html");
        //when
        htmlDocumentInterpreter.mapDocument(scpDocument, pageContent);
        //then
        assertEquals(23, pageContent.getContent().childNodeSize());
    }

}