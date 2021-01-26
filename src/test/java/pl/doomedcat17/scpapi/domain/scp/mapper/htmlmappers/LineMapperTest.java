package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.Appendix;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LineMapperTest extends MapperTest {

    private final LineMapper lineMapper = new LineMapper();

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/lines/SampleLinesElements.html");

    private final Map<String, List<Appendix>> expectedOutputs = TestDataProvider
            .getExpectedAppendicesOutputs("src/test/resources/html/test_data/lines/expected_outputs.json");

    @Test
    void shouldMapSimpleLine() {
        //given
        Element simpleLine = sampleLines.getElementById("sampleLine");
        //when
        Appendix actualAppendix = lineMapper.mapElement(simpleLine);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleLine").get(0), actualAppendix);
    }


    @Test
    void shouldMapLineWithHeader() {
        //given
        Element lineWithHeader = sampleLines.getElementById("sampleLineWithHeader");
        //when
        Appendix actualAppendix = lineMapper.mapElement(lineWithHeader);
        //then
        assertEquals(expectedOutputs.get("shouldMapLineWithHeader").get(0), actualAppendix);

    }
    @Test
    void shouldMapOnlyContent() {
        //given
        Element line = sampleLines.getElementById("lineWithStrongElements");
        Element innerLineWithHeader = sampleLines.getElementById("innerSampleLineWithHeader");
        //when
        Appendix actualAppendix = lineMapper.mapElement(line);
        Appendix actualInnerAppendix = lineMapper.mapElement(innerLineWithHeader);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldMapOnlyContent").get(0), actualAppendix),
                () -> assertEquals(expectedOutputs.get("innerSampleLineWithHeader").get(0), actualInnerAppendix));

    }

}