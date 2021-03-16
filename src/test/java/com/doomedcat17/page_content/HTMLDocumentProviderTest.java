package com.doomedcat17.page_content;

import com.doomedcat17.scpapi.TestDataProvider;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.doomedcat17.scpier.page_content.html.document.HTMLDocumentProvider;

import static org.junit.jupiter.api.Assertions.*;

class HTMLDocumentProviderTest {

    HTMLDocumentProvider htmlDocumentProvider = new HTMLDocumentProvider();


    @Disabled
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