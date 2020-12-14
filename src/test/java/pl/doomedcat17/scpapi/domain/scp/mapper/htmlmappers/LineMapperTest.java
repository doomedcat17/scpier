package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.ScpObject;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LineMapperTest extends MapperTest {

    private final LineMapper lineMapper = new LineMapper();

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/lines/SampleLinesElements.html");

    private final Map<String, ScpObject> expectedOutputs = TestDataProvider
            .getExpectedOutputs("src/test/resources/html/test_data/lines/expected_outputs.json");

    @Test
    void shouldMapSimpleLine() {
        //given
        Element simpleLine = sampleLines.getElementById("sampleLine");
        addSampleAppendixToScpObject();
        //when
        lineMapper.mapElement(simpleLine, scpObject);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleLine"), scpObject);
    }


    @Test
    void shouldMapLineWithHeader() {
        //given
        Element lineWithHeader = sampleLines.getElementById("sampleLineWithHeader");
        //when
        lineMapper.mapElement(lineWithHeader, scpObject);
        //then
        assertEquals(expectedOutputs.get("shouldMapLineWithHeader"), scpObject);

    }
    @Test
    void shouldMapOnlyContent() {
        //given
        Element line = sampleLines.getElementById("lineWithStrongElements");
        addSampleAppendixToScpObject();
        //when
        lineMapper.mapElement(line, scpObject);
        //then
        assertEquals(expectedOutputs.get("shouldMapOnlyContent"), scpObject);

    }
    @Test
    void shouldMapMultipleLinesToSingleAppendix() {
        //given
        Element simpleLine = sampleLines.getElementById("sampleLine");
        addSampleAppendixToScpObject();
        //when
        for (int i = 0; i < 3; i++) {
            lineMapper.mapElement(simpleLine, scpObject);
        }
        //then
        assertAll(() -> assertEquals(1, scpObject.getAppendices().size()),
                () -> assertEquals(4, scpObject.getLastAppendix().getContents().size()));

    }

    @Test
    void shouldMapLinesToMultipleAppendices() {
        //given
        List<Element> elements = List.of(
                sampleLines.getElementById("sampleLineWithHeader"),
                sampleLines.getElementById("sampleLineWithHeader"),
                sampleLines.getElementById("sampleLineWithHeader2"),
                sampleLines.getElementById("sampleLineWithHeader3"),
                sampleLines.getElementById("sampleLineWithHeader3"));
        addSampleAppendixToScpObject();
        //when
        elements.forEach(element -> lineMapper.mapElement(element, scpObject));
        //then
        assertEquals(expectedOutputs.get("shouldMapLinesToMultipleAppendices"), scpObject);

    }
}