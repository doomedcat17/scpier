package pl.doomedcat17.scpapi.domain.scp.http;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;

import static org.junit.jupiter.api.Assertions.*;

class HTMLDocumentProviderImplTest {

    HTMLDocumentProvider htmlDocumentProvider = new HTMLDocumentProviderImpl();


    @Test
    void shouldReturnScpDocument() {
        //given
        Element expectedContent = TestDataProvider.getSamplePageContent();

        try {
            //when
            Element actualContent = htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-012")
                    .getElementById("page-content");
            //then
            assertEquals(expectedContent.html(), actualContent.html());
        } catch (Exception e) {
            fail();
        }
    }

}