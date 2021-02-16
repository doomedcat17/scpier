package mapper.htmlscrappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import data.appendencies.Appendix;
import mapper.htmlscrappers.list.ListScrapper;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ListScrapperTest extends ScrapperTest {

    private ListScrapper listMapper = new ListScrapper();

    private final Element sampleLists = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/lists/SampleListsElements.html");

    private Map<String, List<Appendix>> expectedOutputs =
            getExpectedAppendicesOutputs("src/test/resources/html/test_data/lists/expected_outputs.json");

    @Test
    void shouldScrapSimpleUnorderedList() {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapSimpleUnorderedList");
        //when
        Appendix actualAppendix = listMapper.scrapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleUnorderedList").get(0), actualAppendix);
    }
    @Test
    void shouldScrapSimpleOrderedList() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapSimpleOrderedList");
        //when
        Appendix actualAppendix = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleOrderedList").get(0), actualAppendix);
    }


    @Test
    void shouldScrapUnorderedListWithNestedUnorderedList() {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapUnorderedListWithNestedUnorderedList");
        //when
        Appendix actualAppendix = listMapper.scrapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapUnorderedListWithNestedUnorderedList").get(0), actualAppendix);
    }

    @Test
    void shouldScrapOrderedListWithNestedUnorderedList() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapOrderedListWithNestedUnorderedList");
        //when
        Appendix actualAppendix = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapOrderedListWithNestedUnorderedList").get(0), actualAppendix);
    }
    @Test
    void shouldScrapUnorderedListWithNestedOrderedList() {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapUnorderedListWithNestedOrderedList");
        //when
        Appendix actualAppendix = listMapper.scrapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapUnorderedListWithNestedOrderedList").get(0), actualAppendix);
    }
    @Test
    void shouldScrapOrderedListWithNestedOrderedList() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapOrderedListWithNestedOrderedList");
        //when
        Appendix actualAppendix = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapOrderedListWithNestedOrderedList").get(0), actualAppendix);
    }


    @Test
    void shouldScrapUnorderedListWithNestedMultipleUnorderedLists() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapUnorderedListWithNestedMultipleUnorderedLists");
        //when
        Appendix actualAppendix = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapUnorderedListWithNestedMultipleUnorderedLists").get(0), actualAppendix);
    }

    @Test
    void shouldScrapOrderedListWithNestedMultipleUnorderedLists() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapOrderedListWithNestedMultipleUnorderedLists");
        //when
        Appendix actualAppendix = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapOrderedListWithNestedMultipleUnorderedLists").get(0), actualAppendix);
    }

    @Test
    void shouldScrapUnorderedListWithNestedMultipleOrderedLists() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapUnorderedListWithNestedMultipleOrderedLists");
        //when
        Appendix actualAppendix = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapUnorderedListWithNestedMultipleOrderedLists").get(0), actualAppendix);
    }

    @Test
    void shouldScrapOrderedListWithNestedMultipleOrderedLists() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldScrapOrderedListWithNestedMultipleOrderedLists");
        //when
        Appendix actualAppendix = listMapper.scrapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldScrapOrderedListWithNestedMultipleOrderedLists").get(0), actualAppendix);
    }

    @Test
    void shouldScrapSimpleUnorderedListWithDeletedElements() {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleUnorderedListWithDeletedElements");
        //when
        Appendix actualAppendix = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleUnorderedListWithDeletedElements").get(0), actualAppendix);
    }

    @Test
    void shouldScrapSimpleOrderedListWithDeletedElements() {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleOrderedListWithDeletedElements");
        //when
        Appendix actualAppendix = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleOrderedListWithDeletedElements").get(0), actualAppendix);
    }

    @Test
    void shouldScrapSimpleUnorderedListWithStrongElements() {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleUnorderedListWithStrongElements");
        //when
        Appendix actualAppendix = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleUnorderedListWithStrongElements").get(0), actualAppendix);
    }

    @Test
    void shouldScrapSimpleOrderedListWithStrongElements() {
        //given
        Element list = sampleLists.getElementById("shouldScrapSimpleOrderedListWithStrongElements");
        //when
        Appendix actualAppendix = listMapper.scrapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleOrderedListWithStrongElements").get(0), actualAppendix);
    }
}