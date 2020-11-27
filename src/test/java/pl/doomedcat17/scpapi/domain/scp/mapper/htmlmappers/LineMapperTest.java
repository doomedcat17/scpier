package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.data.Appendix;

import static org.junit.jupiter.api.Assertions.*;

class LineMapperTest {


    private LineMapper lineMapper = new LineMapper();

    @Test
    void shouldMapSimpleLine() {
        //given
        Element simpleLine = Jsoup.parse("<p>Following initial investigations, multiple test subjects were allowed access to the score. In every case, the subjects mutilated themselves in order to use their own blood to finish the piece, resulting in subsequent symptoms of psychosis and massive trauma. Those subjects who managed to finish a section of the piece immediately committed suicide, declaring the piece to be \"impossible to complete\". Attempts to perform the music have resulted in a disagreeable cacophony, with each instrumental part having no correlation or harmony with the other instruments.</p>");
        //when
        Appendix<String> actualAppendix = lineMapper.mapElement(simpleLine);
        //then
        assertEquals(simpleLine.text(), actualAppendix.getContent());
    }

    @Test
    void shouldMapLineWithHeader() {
        //given
        Element lineWithHeader = Jsoup.parse("<p><strong>Description:</strong> SCP-012 was retrieved by Archaeologist K.M. Sandoval during the excavation of a northern Italian tomb destroyed in a recent storm. The object, a piece of handwritten musical score entitled \"On Mount Golgotha\", part of a larger set of sheet music, appears to be incomplete. The red/black ink, first thought to be some form of berry or natural dye ink, was later found to be human blood from multiple subjects. The first personnel to locate the sheet (Site 19 Special Salvage) had two (2) members descend into insanity, attempting to use their own blood to finish the composition, ultimately resulting in massive blood loss and internal trauma.</p>");
        //when
        Appendix<String> actualAppendix = lineMapper.mapElement(lineWithHeader);
        //then
        assertAll(() -> assertEquals(lineWithHeader.text().substring(13), actualAppendix.getContent()),
                  () -> assertEquals("Description", actualAppendix.getTitle()));

    }

    @Test
    void shouldMapOnlyContent() {
        //given
        Element line = Jsoup.parse("<p><strong>Date:</strong> ██/██/████<br>" +
                "<strong>Objects Observed:</strong> Bibles<br>" +
                "<strong>Notes:</strong> Due to the wide variation in sizes of individual copies, estimates of the number of Bibles present ranged from 3 billion to 6 billion. The pile was searched for early copies of potential historical significance, but given the number of copies present an exhaustive search was not possible. The earliest copy located dated to 1607.</p>");
        //when
        Appendix<String> actualAppendix = lineMapper.mapElement(line);
        //then
        assertAll(() -> assertEquals(line.text(), actualAppendix.getContent()));

    }

}