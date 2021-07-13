package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.list.ListScrapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListScrapperTest extends ScrapperTest {

    private final ListScrapper listMapper = new ListScrapper(SOURCE);

    private final Element sampleLists = TestDataProvider
            .getSampleElements("src/test/resources/html/testdata/lists/SampleListsElements.html");

    private final Map<String, ContentNode<?>> expectedOutputs =
            getExpectedAppendicesOutputs("src/test/resources/html/testdata/lists/expected_outputs.json");

    @Test
    void shouldScrapSimpleUnorderedList() throws ElementScrapperException {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapSimpleUnorderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleUnorderedList"), contentNode);
    }
    @Test
    void shouldScrapSimpleOrderedList() throws ElementScrapperException {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapSimpleOrderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleOrderedList"), contentNode);
    }


    @Test
    void shouldScrapUnorderedListWithNestedUnorderedList() throws ElementScrapperException {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapUnorderedListWithNestedUnorderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapUnorderedListWithNestedUnorderedList"), contentNode);
    }

    @Test
    void shouldScrapOrderedListWithNestedUnorderedList() throws ElementScrapperException {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapOrderedListWithNestedUnorderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapOrderedListWithNestedUnorderedList"), contentNode);
    }
    @Test
    void shouldScrapUnorderedListWithNestedOrderedList() throws ElementScrapperException {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapUnorderedListWithNestedOrderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapUnorderedListWithNestedOrderedList"), contentNode);
    }
    @Test
    void shouldScrapOrderedListWithNestedOrderedList() throws ElementScrapperException {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapOrderedListWithNestedOrderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapOrderedListWithNestedOrderedList"), contentNode);
    }


    @Test
    void shouldScrapUnorderedListWithNestedMultipleUnorderedLists() throws ElementScrapperException {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapUnorderedListWithNestedMultipleUnorderedLists");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapUnorderedListWithNestedMultipleUnorderedLists"), contentNode);
    }

    @Test
    void shouldScrapOrderedListWithNestedMultipleUnorderedLists() throws ElementScrapperException {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapOrderedListWithNestedMultipleUnorderedLists");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapOrderedListWithNestedMultipleUnorderedLists"), contentNode);
    }

    @Test
    void shouldScrapUnorderedListWithNestedMultipleOrderedLists() throws ElementScrapperException {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapUnorderedListWithNestedMultipleOrderedLists");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapUnorderedListWithNestedMultipleOrderedLists"), contentNode);
    }

    @Test
    void shouldScrapOrderedListWithNestedMultipleOrderedLists() throws ElementScrapperException {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapOrderedListWithNestedMultipleOrderedLists");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapOrderedListWithNestedMultipleOrderedLists"), contentNode);
    }

    @Test
    void shouldScrapSimpleUnorderedListWithDeletedElements() throws ElementScrapperException {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleUnorderedListWithDeletedElements");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleUnorderedListWithDeletedElements"), contentNode);
    }

    @Test
    void shouldScrapSimpleOrderedListWithDeletedElements() throws ElementScrapperException {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleOrderedListWithDeletedElements");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleOrderedListWithDeletedElements"), contentNode);
    }

    @Test
    void shouldScrapSimpleUnorderedListWithStrongElements() throws ElementScrapperException {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleUnorderedListWithStrongElements");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleUnorderedListWithStrongElements"), contentNode);
    }

    @Test
    void shouldScrapSimpleOrderedListWithStrongElements() throws ElementScrapperException {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleOrderedListWithStrongElements");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleOrderedListWithStrongElements"), contentNode);
    }
}