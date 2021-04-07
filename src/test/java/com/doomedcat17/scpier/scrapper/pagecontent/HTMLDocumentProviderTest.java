package com.doomedcat17.scpier.scrapper.pagecontent;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.pagecontent.html.document.HTMLDocumentProvider;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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