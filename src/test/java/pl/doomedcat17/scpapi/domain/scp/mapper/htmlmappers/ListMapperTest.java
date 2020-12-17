package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.Appendix;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ListMapperTest extends MapperTest {

    private ListMapper listMapper = new ListMapper();

    private final Element sampleLists = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/lists/SampleListsElements.html");

    private final Map<String, List<Appendix>> expectedOutputs = TestDataProvider
            .getExpectedAppendicesOutputs("src/test/resources/html/test_data/lists/expected_outputs.json");

    @Test
    void shouldMapSimpleUnorderedList() {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapSimpleUnorderedList");
        //when
        Appendix actualAppendix = listMapper.mapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleUnorderedList").get(0), actualAppendix);
    }
    @Test
    void shouldMapSimpleOrderedList() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapSimpleOrderedList");
        //when
        Appendix actualAppendix = listMapper.mapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleOrderedList").get(0), actualAppendix);
    }



    @Test
    void shouldMapUnorderedListWithNestedUnorderedList() {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapUnorderedListWithNestedUnorderedList");
        //when
        Appendix actualAppendix = listMapper.mapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapUnorderedListWithNestedUnorderedList").get(0), actualAppendix);
    }
    @Test
    void shouldMapOrderedListWithNestedUnorderedList() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapOrderedListWithNestedUnorderedList");
        //when
        Appendix actualAppendix = listMapper.mapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapOrderedListWithNestedUnorderedList").get(0), actualAppendix);
    }
    @Test
    void shouldMapUnorderedListWithNestedOrderedList() {
        //given
        Element simpleUnorderedList = sampleLists.getElementById("shouldMapUnorderedListWithNestedOrderedList");
        //when
        Appendix actualAppendix = listMapper.mapElement(simpleUnorderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapUnorderedListWithNestedOrderedList").get(0), actualAppendix);
    }
    @Test
    void shouldMapOrderedListWithNestedOrderedList() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapOrderedListWithNestedOrderedList");
        //when
        Appendix actualAppendix = listMapper.mapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapOrderedListWithNestedOrderedList").get(0), actualAppendix);
    }


    @Test
    void shouldMapUnorderedListWithNestedMultipleUnorderedList() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapUnorderedListWithNestedMultipleUnorderedList");
        //when
        Appendix actualAppendix = listMapper.mapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapUnorderedListWithNestedMultipleUnorderedList").get(0), actualAppendix);
    }

    @Test
    void shouldMapOrderedListWithNestedMultipleUnorderedList() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapOrderedListWithNestedMultipleUnorderedList");
        //when
        Appendix actualAppendix = listMapper.mapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapOrderedListWithNestedMultipleUnorderedList").get(0), actualAppendix);
    }

    @Test
    void shouldMapUnorderedListWithNestedMultipleOrderedList() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapUnorderedListWithNestedMultipleOrderedList");
        //when
        Appendix actualAppendix = listMapper.mapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapUnorderedListWithNestedMultipleOrderedList").get(0), actualAppendix);
    }

    @Test
    void shouldMapOrderedListWithNestedMultipleOrderedList() {
        //given
        Element simpleOrderedList = sampleLists.getElementById("shouldMapOrderedListWithNestedMultipleOrderedList");
        //when
        Appendix actualAppendix = listMapper.mapElement(simpleOrderedList);
        //then
        assertEquals(expectedOutputs.get("shouldMapOrderedListWithNestedMultipleOrderedList").get(0), actualAppendix);
    }

    @Test
    void shouldMapSimpleUnorderedListWithDeletedElements() {
        //given
        Element list = sampleLists.getElementById("shouldMapSimpleUnorderedListWithDeletedElements");
        //when
        Appendix actualAppendix = listMapper.mapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleUnorderedListWithDeletedElements").get(0), actualAppendix);
    }

    @Test
    void shouldMapSimpleOrderedListWithDeletedElements() {
        //given
        Element list = sampleLists.getElementById("shouldMapSimpleOrderedListWithDeletedElements");
        //when
        Appendix actualAppendix = listMapper.mapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleOrderedListWithDeletedElements").get(0), actualAppendix);
    }

    @Test
    void shouldMapSimpleUnorderedListWithStrongElements() {
        //given
        Element list = sampleLists.getElementById("shouldMapSimpleUnorderedListWithDeletedElements");
        //when
        Appendix actualAppendix = listMapper.mapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleUnorderedListWithDeletedElements").get(0), actualAppendix);
    }

    @Test
    void shouldMapSimpleOrderedListWithStrongElements() {
        //given
        Element list = sampleLists.getElementById("shouldMapSimpleOrderedListWithDeletedElements");
        //when
        Appendix actualAppendix = listMapper.mapElement(list);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleOrderedListWithDeletedElements").get(0), actualAppendix);
    }



    //TODO strong elements, deleted elements check 
}