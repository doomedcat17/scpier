package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.blockquote.BlockquoteScrapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockquoteScrapperTest extends ScrapperTest {


    private final BlockquoteScrapper blockquoteScrapper = new BlockquoteScrapper(SOURCE);

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/testdata/blockquotes/SampleBlockquoteElements.html");

    private final Map<String, ContentNode<?>> expectedOutputs =
            getExpectedAppendicesOutputs("src/test/resources/html/testdata/blockquotes/expected_outputs.json");

    @Test
    void shouldScrapSimpleBlockquote() throws ElementScrapperException {
        //given
        Element element = sampleLines.getElementById("shouldScrapSimpleBlockquote");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleBlockquote"), contentNode);
    }

    @Test
    void shouldScrapBlockquoteWithLongLine() throws ElementScrapperException {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithLongLine");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithLongLine"), contentNode);
    }
    @Test
    void shouldScrapBlockquoteWithMultipleLines() throws ElementScrapperException {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithMultipleLines");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithMultipleLines"), contentNode);
    }
    @Test
    void shouldScrapBlockquoteWithDeletedContent() throws ElementScrapperException {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithDeletedContent");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithDeletedContent"), contentNode);
    }
    @Test
    void shouldScrapBlockquoteWithList() throws ElementScrapperException {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithList");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithList"), contentNode);
    }

    @Test
    void shouldScrapBlockquoteWithHeading() throws ElementScrapperException {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithHeading");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithHeading"), contentNode);
    }

    @Test
    void shouldScrapBlockquoteWithImage() throws ElementScrapperException {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithImage");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithImage"), contentNode);
    }
}