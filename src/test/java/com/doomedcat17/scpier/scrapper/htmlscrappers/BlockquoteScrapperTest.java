package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.scrapper.blockquote.BlockquoteScrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    void shouldScrapSimpleBlockquote()  {
        //given
        Element element = sampleLines.getElementById("shouldScrapSimpleBlockquote");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleBlockquote"), contentNode);
    }

    @Test
    void shouldScrapBlockquoteWithLongLine()  {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithLongLine");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithLongLine"), contentNode);
    }
    @Test
    void shouldScrapBlockquoteWithMultipleLines()  {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithMultipleLines");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithMultipleLines"), contentNode);
    }
    @Test
    void shouldScrapBlockquoteWithDeletedContent()  {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithDeletedContent");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithDeletedContent"), contentNode);
    }
    @Test
    void shouldScrapBlockquoteWithList() throws JsonProcessingException {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithList");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithList"), contentNode);
    }

    @Test
    void shouldScrapBlockquoteWithHeading()  {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithHeading");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithHeading"), contentNode);
    }

    @Test
    void shouldScrapBlockquoteWithImage()  {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithImage");
        //when
        ContentNode<?> contentNode = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithImage"), contentNode);
    }
}