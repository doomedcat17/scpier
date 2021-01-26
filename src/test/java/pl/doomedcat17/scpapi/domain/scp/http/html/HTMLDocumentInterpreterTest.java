package pl.doomedcat17.scpapi.domain.scp.http.html;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.HTMLDocumentInterpreter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HTMLDocumentInterpreterTest {

    HTMLDocumentInterpreter htmlDocumentInterpreter = new HTMLDocumentInterpreter();

    @Test
    void shouldClearContent() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-035.html");
        //when
        Element scpContentElement = htmlDocumentInterpreter.parseDocument(scpDocument);
        //then
        assertEquals(31, scpContentElement.childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-194.html");
        //when
        Element scpContentElement = htmlDocumentInterpreter.parseDocument(scpDocument);
        //then
        assertEquals(7, scpContentElement.childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv2() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-285.html");
        //when
        Element scpContentElement = htmlDocumentInterpreter.parseDocument(scpDocument);
        //then
        assertEquals(34, scpContentElement.childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv3() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-1496.html");
        //when
        Element scpContentElement = htmlDocumentInterpreter.parseDocument(scpDocument);
        //then
        assertEquals(6, scpContentElement.childNodeSize());
    }

    @Test
    void shouldUnpackScpFromDiv4() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-2117.html");
        //when
        Element scpContentElement = htmlDocumentInterpreter.parseDocument(scpDocument);
        //then
        assertEquals(38, scpContentElement.childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-597.html");
        //when
        Element scpContentElement = htmlDocumentInterpreter.parseDocument(scpDocument);
        //then
        assertEquals(36, scpContentElement.childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent2() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-1004.html");
        //when
        Element scpContentElement = htmlDocumentInterpreter.parseDocument(scpDocument);
        //then
        assertEquals(14, scpContentElement.childNodeSize());
    }

    @Test
    void shouldGetRedirectedContent3() throws IOException {
        //given
        Document scpDocument = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-2182.html");
        //when
        Element scpContentElement = htmlDocumentInterpreter.parseDocument(scpDocument);
        //then
        assertEquals(23, scpContentElement.childNodeSize());
    }

}