package com.doomedcat17.scpier.page.html.document.revision;

import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import org.jsoup.nodes.Element;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LastRevisionDateScraperTest {

    private final LastRevisionDateScraper lastRevisionDateScraper = new LastRevisionDateScraper();

    @ParameterizedTest
    @CsvSource(value = {"scp2.html:1588023372", "scp-285.html:1600446633", "scp-194.html:1601089712"}, delimiterString = ":")
    void shouldScrapDate(String sampleScpFileName, long expected) throws RevisionDateException {
        //given
        Element content = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/test_data/sample_scps/"+sampleScpFileName);
        //when
        LocalDateTime foundDate = lastRevisionDateScraper.scrapDate(content);
        //then
        assertEquals(foundDate, LocalDateTime.ofEpochSecond(expected, 0, ZoneOffset.UTC));
    }
}