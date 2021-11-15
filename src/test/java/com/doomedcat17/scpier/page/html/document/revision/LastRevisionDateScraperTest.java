package com.doomedcat17.scpier.page.html.document.revision;

import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LastRevisionDateScraperTest {

    private final LastRevisionDateScraper lastRevisionDateScraper = new LastRevisionDateScraper();

    @Test
    void shouldScrapDate() throws RevisionDateException {
        //given
        Element content = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp2.html");
        //when
        Date foundDate = lastRevisionDateScraper.scrapDate(content);
        //then
        assertEquals(foundDate, new Date(1588023372000L));
    }

    @Test
    void shouldScrapDate2() throws RevisionDateException {
        //given
        Element content = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-285.html");
        //when
        Date foundDate = lastRevisionDateScraper.scrapDate(content);
        //then
        assertEquals(foundDate, new Date(1600446633000L));
    }

    @Test
    void shouldScrapDate3() throws RevisionDateException {
        //given
        Element content = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/scp-194.html");
        //when
        Date foundDate = lastRevisionDateScraper.scrapDate(content);
        //then
        assertEquals(foundDate, new Date(1601089712000L));
    }

}