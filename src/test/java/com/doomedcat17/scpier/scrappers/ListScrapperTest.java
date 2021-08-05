package com.doomedcat17.scpier.scrappers;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ListNode;
import com.doomedcat17.scpier.scraper.list.ListScraper;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListScrapperTest extends ScrapperTest {

    private final ListScraper listMapper = new ListScraper(SOURCE);

    private final Element sampleLists = TestDataProvider
            .getSampleElement("src/test/resources/html/test_data/lists/sample-lists-elements.html");

    private final Map<String, ContentNode<?>> expectedOutputs =
            getExpectedContentNodeOutputs("src/test/resources/html/test_data/lists/expected_outputs.json");


    @Test
    void shouldScrapSimpleUnorderedList() throws IOException {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapSimpleUnorderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleUnorderedList"), contentNode);
    }
    @Test
    void shouldScrapSimpleOrderedList()  {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapSimpleOrderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleOrderedList"), contentNode);
    }


    @Test
    void shouldScrapUnorderedListWithNestedUnorderedList()  {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapUnorderedListWithNestedUnorderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapUnorderedListWithNestedUnorderedList"), contentNode);
    }

    @Test
    void shouldScrapOrderedListWithNestedUnorderedList()  {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapOrderedListWithNestedUnorderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapOrderedListWithNestedUnorderedList"), contentNode);
    }
    @Test
    void shouldScrapUnorderedListWithNestedOrderedList()  {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapUnorderedListWithNestedOrderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapUnorderedListWithNestedOrderedList"), contentNode);
    }
    @Test
    void shouldScrapOrderedListWithNestedOrderedList()  {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapOrderedListWithNestedOrderedList");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapOrderedListWithNestedOrderedList"), contentNode);
    }


    @Test
    void shouldScrapUnorderedListWithNestedMultipleUnorderedLists()  {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapUnorderedListWithNestedMultipleUnorderedLists");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapUnorderedListWithNestedMultipleUnorderedLists"), contentNode);
    }

    @Test
    void shouldScrapOrderedListWithNestedMultipleUnorderedLists()  {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapOrderedListWithNestedMultipleUnorderedLists");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapOrderedListWithNestedMultipleUnorderedLists"), contentNode);
    }

    @Test
    void shouldScrapUnorderedListWithNestedMultipleOrderedLists()  {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapUnorderedListWithNestedMultipleOrderedLists");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapUnorderedListWithNestedMultipleOrderedLists"), contentNode);
    }

    @Test
    void shouldScrapOrderedListWithNestedMultipleOrderedLists()  {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapOrderedListWithNestedMultipleOrderedLists");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapOrderedListWithNestedMultipleOrderedLists"), contentNode);
    }

    @Test
    void shouldScrapSimpleUnorderedListWithDeletedElements()  {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleUnorderedListWithDeletedElements");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleUnorderedListWithDeletedElements"), contentNode);
    }

    @Test
    void shouldScrapSimpleOrderedListWithDeletedElements()  {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleOrderedListWithDeletedElements");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleOrderedListWithDeletedElements"), contentNode);
    }

    @Test
    void shouldScrapSimpleUnorderedListWithStrongElements()  {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleUnorderedListWithStrongElements");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleUnorderedListWithStrongElements"), contentNode);
    }

    @Test
    void shouldScrapSimpleOrderedListWithStrongElements() throws IOException {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleOrderedListWithStrongElements");
        //when
        ContentNode<?> contentNode = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleOrderedListWithStrongElements"), contentNode);
    }

    @Test
    void shouldIgnoreBlankRow() throws IOException {
        //given
        Element list = sampleLists.getElementById("shouldIgnoreBlankRow");
        //when
        ListNode<?> contentNode = (ListNode<?>) listMapper.scrapElement(list);
        //then
        assertEquals(4, contentNode.getContent().size());
    }

}