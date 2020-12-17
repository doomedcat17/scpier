package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.Appendix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BlockquoteMapperTest {


    private BlockquoteMapper blockquoteMapper = new BlockquoteMapper();

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/blockquotes/SampleBlockquoteElements.html");

    private final Map<String, List<Appendix>> expectedOutputs = TestDataProvider
            .getExpectedAppendicesOutputs("src/test/resources/html/test_data/blockquotes/expected_outputs.json");

    @Test
    void shouldMapSimpleBlockquote() {
        //given
        Element element = sampleLines.getElementById("shouldMapSimpleBlockquote");
        //when
        Appendix actualAppendix = blockquoteMapper.mapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleBlockquote").get(0), actualAppendix);
    }

    @Test
    void shouldMapBlockquoteWithLongLine() {
        //given
        Element element = sampleLines.getElementById("shouldMapBlockquoteWithLongLine");
        //when
        Appendix actualAppendix = blockquoteMapper.mapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldMapBlockquoteWithLongLine").get(0), actualAppendix);
    }
    @Test
    void shouldMapBlockquoteWithMultipleLines() {
        //given
        Element element = sampleLines.getElementById("shouldMapBlockquoteWithMultipleLines");
        //when
        Appendix actualAppendix = blockquoteMapper.mapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldMapBlockquoteWithMultipleLines").get(0), actualAppendix);
    }
    @Test
    void shouldMapBlockquoteWithDeletedContent() {
        //given
        Element element = sampleLines.getElementById("shouldMapBlockquoteWithDeletedContent");
        //when
        Appendix actualAppendix = blockquoteMapper.mapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldMapBlockquoteWithDeletedContent").get(0), actualAppendix);
    }
    @Test
    void shouldMapBlockquoteWithList() {
        //given
        Element element = sampleLines.getElementById("shouldMapBlockquoteWithList");
        //when
        Appendix actualAppendix = blockquoteMapper.mapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldMapBlockquoteWithList").get(0), actualAppendix);
    }
}