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
        assertEquals(expectedOutputs.get("shouldMapSimpleLine").get(0), actualAppendix);
    }
}