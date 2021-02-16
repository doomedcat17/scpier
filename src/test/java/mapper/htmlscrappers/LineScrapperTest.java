package mapper.htmlscrappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import data.appendencies.Appendix;
import mapper.htmlscrappers.line.LineScrapper;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LineScrapperTest extends ScrapperTest {

    private final LineScrapper lineMapper = new LineScrapper();

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/lines/SampleLinesElements.html");

    private Map<String, List<Appendix>> expectedOutputs = getExpectedAppendicesOutputs("src/test/resources/html/test_data/lines/expected_outputs.json");

    @Test
    void shouldScrapSimpleLine() {
        //given
        Element simpleLine = sampleLines.getElementById("sampleLine");
        //when
        Appendix actualAppendix = lineMapper.scrapElement(simpleLine);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleLine").get(0), actualAppendix);
    }

    @Test
    void shouldScrapSimpleLineWithDeletedContent() {
        //given
        Element simpleLine = sampleLines.getElementById("sampleLineWithDeletedContent");
        //when
        Appendix actualAppendix = lineMapper.scrapElement(simpleLine);
        //then
        assertEquals(expectedOutputs.get("sampleLineWithDeletedContent").get(0), actualAppendix);
    }

    @Test
    void shouldScrapLineWithHeader() {
        //given
        Element lineWithHeader = sampleLines.getElementById("shouldScrapLineWithHeader");
        //when
        Appendix actualAppendix = lineMapper.scrapElement(lineWithHeader);
        //then
        assertEquals(expectedOutputs.get("shouldScrapLineWithHeader").get(0), actualAppendix);

    }
    @Test
    void shouldScrapLineWithStrongElements() {
        //given
        Element line = sampleLines.getElementById("shouldScrapContentWithStrongElements");
        //when
        Appendix actualAppendix = lineMapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapContentWithStrongElements").get(0), actualAppendix));

    }

    @Test
    void shouldScrapLineWithEmAndIElements() {
        //given
        Element line = sampleLines.getElementById("shouldScrapContentWithEmAndIElements");
        //when
        Appendix actualAppendix = lineMapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapContentWithEmAndIElements").get(0), actualAppendix));

    }

    @Test
    void shouldScrapLineWithInnerSpanElements() {
        //given
        Element line = sampleLines.getElementById("shouldScrapContentWithInnerSpanElements");
        //when
        Appendix actualAppendix = lineMapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapContentWithInnerSpanElements").get(0), actualAppendix));

    }

    @Test
    void shouldScrapLineWithStylization() {
        //given
        Element line = sampleLines.getElementById("shouldScrapContentWithStylization");
        //when
        Appendix actualAppendix = lineMapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapContentWithStylization").get(0), actualAppendix));

    }
    @Test
    void shouldScrapStrongElement() {
        //given
        Element line = sampleLines.getElementById("sampleStrong");
        //when
        Appendix actualAppendix = lineMapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapStrongElement").get(0), actualAppendix));
    }

    @Test
    void shouldScrapStrongElementAsTextNode() {
        //given
        Element line = sampleLines.getElementById("sampleStrong2");
        //when
        Appendix actualAppendix = lineMapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapStrongElementAsTextNode").get(0), actualAppendix));
    }

    @Test
    void shouldScrapLineWithMultipleStrongElements() {
        //given
        Element line = sampleLines.getElementById("shouldScrapLineWithMultipleStrongElements");
        //when
        Appendix actualAppendix = lineMapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapLineWithMultipleStrongElements").get(0), actualAppendix));
    }

}