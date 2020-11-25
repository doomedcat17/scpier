package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LineMapperTest {


    private HtmlMapper htmlMapper = new LineMapper();

    @Test
    void shouldMapParagraphWithHeader() {
        //given
        Element contentElement = TestDataProvider.getSamplePageContent();
        Element paragraphElement = contentElement.selectFirst("p");
        //when
        Map<String, String> actualMapping = htmlMapper.mapElement(paragraphElement);
        //then
        assertAll(
                () -> assertTrue(actualMapping.containsKey("title")),
                () -> assertTrue(actualMapping.containsValue("SCP-012"))
        );
    }

    @Test
    void shouldMapParagraphWithoutHeader() {
        //given
        Element contentElement = TestDataProvider.getSamplePageContent();
        Element paragraphElement = contentElement.children().get(contentElement.childrenSize()-2); //last <p> element
        String expectedText = "Following initial investigations, multiple test subjects were allowed access to the score. In every case, the subjects mutilated themselves in order to use their own blood to finish the piece, resulting in subsequent symptoms of psychosis and massive trauma. Those subjects who managed to finish a section of the piece immediately committed suicide, declaring the piece to be \"impossible to complete\". Attempts to perform the music have resulted in a disagreeable cacophony, with each instrumental part having no correlation or harmony with the other instruments.";
        //when
        Map<String, String> actualMapping = htmlMapper.mapElement(paragraphElement);
        //then
        assertAll(
                () -> assertTrue(actualMapping.containsKey("content")),
                () -> assertTrue(actualMapping.containsValue(expectedText))
        );
    }

    @Test
    void shouldMapParagraphWithDeletedText() {
        //given
        Element paragraphElement = Jsoup
                .parse("<p><span style=\"text-decoration: line-through;\">SCP-192-1 appears to absorb this radiation emission, although all testing performed so far on SCP-192-1 has shown it to be constructed out of regular materials for this kind of machine.</span></p>",
                        "",
                        Parser.xmlParser());
        //when
        Map<String, String> actualMapping = htmlMapper.mapElement(paragraphElement);
        //then
        assertAll(
                () -> assertTrue(actualMapping.containsKey("content")),
                () -> assertTrue(actualMapping.get("content").contains("[DELETED]"))
        );
        System.out.println(actualMapping.get("content"));
    }

    @Test
    void shouldMapParagraphWithHeaderAndDeletedText() {
        //given
        Element paragraphElement = Jsoup
                .parse("<p><strong>Special Containment Procedures:</strong> Reports of forest fires with unknown/unusual causes are to be constantly monitored, with an increased priority in regions with a history of SCP-3032 instances. [Consult Document 3032-Regions for further information]<br>" +
                                "<span style=\"text-decoration: line-through;\">Identified instances of SCP-3032 outside of containment are to be neutralized via aerial-strike by local Armed Observation Posts</span> [See Incident Report 3032-01]. Once an instance of SCP-3032 has been identified, it is to be immediately reported to the nearest Biological Containment Site equipped for SCP-3032. Identified instances are to be kept under constant surveillance; should it enter an active state, the appropriate Site is to deploy anti-air guided missiles in order to neutralize the object before it enters Phase 7. Should object succeed in entering Phase 7, Foundation personnel are to be deployed to destroy any cones that have been released. A thin acid spray has been found to be the most effective method thus far for quick disposal of cones.</p>",
                        "",
                        Parser.xmlParser());
        //when
        Map<String, String> actualMapping = htmlMapper.mapElement(paragraphElement);
        //then
        assertAll(
                () -> assertTrue(actualMapping.containsKey("title")),
                () -> assertTrue(actualMapping.containsValue("Special Containment Procedures")),
                () -> assertTrue(actualMapping.containsKey("content")),
                () -> assertTrue(actualMapping.get("content").contains("[DELETED]"))
        );
        System.out.println(actualMapping.get("content"));
    }

}