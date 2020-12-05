package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.Appendix;

import static org.junit.jupiter.api.Assertions.*;

class LineMapperTest extends MapperTest {

    private final LineMapper lineMapper = new LineMapper();

    private final Element sampleLines = TestDataProvider.getSampleLines();

    @Test
    void shouldMapSimpleLine() {
        //given
        Element simpleLine = sampleLines.getElementById("sampleLine");
        //when
        lineMapper.mapElement(simpleLine, scpObject);
        //then
        assertEquals(simpleLine.text(), scpObject.getLastAppendix().getLastContentBox().getContent());
    }


    @Test
    void shouldMapLineWithHeader() {
        //given
        Element lineWithHeader = sampleLines.getElementById("sampleLineWithHeader");
        //when
        lineMapper.mapElement(lineWithHeader, scpObject);
        //then
        assertAll(() -> assertEquals(lineWithHeader.text().substring(13), scpObject.getLastAppendix().getLastContentBox().getContent()),
                  () -> assertEquals("Description", scpObject.getLastAppendix().getTitle()));

    }
    @Test
    void shouldMapOnlyContent() {
        //given
        Element line = sampleLines.getElementById("lineWithStrongElements");
        //when
        lineMapper.mapElement(line, scpObject);
        //then
        assertAll(() -> assertEquals(line.text(), scpObject.getLastAppendix().getLastContentBox().getContent()));

    }
    @Test
    void shouldMapMultipleLinesToSingleAppendix() {
        //given
        Element simpleLine = sampleLines.getElementById("sampleLine");
        //when
        for (int i = 0; i < 3; i++) {
            lineMapper.mapElement(simpleLine, scpObject);
        }
        //then
        assertAll(() -> assertEquals(1, scpObject.getAppendices().size()),
                () -> assertEquals(testTitle, scpObject.getLastAppendix().getTitle()),
                () -> assertEquals(4, scpObject.getLastAppendix().getContents().size()));

    }

    @Test
    void shouldMapLinesToMultipleAppendices() {
        //given
        Element lineWithHeader = sampleLines.getElementById("sampleLineWithHeader");
        //when
        for (int i = 0; i < 3; i++) {
            lineMapper.mapElement(lineWithHeader, scpObject);
        }

        //then
        boolean areContentsSame = true;
        for (Appendix appendix: scpObject.getAppendices()) {
            if (!(appendix.getTitle().equals(testTitle)) && !appendix.getLastContentBox().getContent().equals(lineWithHeader.text().substring(13))) {
                areContentsSame = false;
                break;
            }
        }

        boolean finalAreContentsSame = areContentsSame;
        assertAll(() -> assertTrue(finalAreContentsSame),
                () -> assertEquals("Description", scpObject.getLastAppendix().getTitle()),
        () -> assertEquals(4, scpObject.getAppendices().size()));

    }
}