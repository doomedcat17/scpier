package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.div.DivMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DivMapperTest {

    private DivMapper divMapper = new DivMapper();

    private final Element sampleDivs = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/divs/SampleDivsElements.html");

    @Test
    void shouldMapDivAsText() {
        //given
        Element simpleDiv = sampleDivs.getElementById("shouldMapDivAsText");
        //when
        Appendix appendix = divMapper.mapElement(simpleDiv);
        //then
        assertAll(
                () -> assertEquals(1, appendix.getContents().size()),
                () -> assertEquals("Sample Text", appendix.getLastContentNode().getContent())

        );

    }

    @Test
    void shouldMapDivWithOnlyText() {
        //given
        Element simpleDiv = sampleDivs.getElementById("shouldMapDivWithOnlyText");
        //when
        Appendix appendix = divMapper.mapElement(simpleDiv);
        //then
        try {
            List<ContentNode<String>> contentNodes = (List<ContentNode<String>>) appendix.getLastContentNode().getContent();
            assertAll(
                    () -> assertEquals(1, appendix.getContents().size()),
                    () -> assertEquals(ContentNodeType.DIV, appendix.getLastContentNode().getContentNodeType()),
                    () -> assertEquals(4, contentNodes.size()),
                    () -> assertEquals("Sample Text", contentNodes.get(0).getContent())

            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void shouldMapDivWithHeading() {
        //given
        Element element = sampleDivs.getElementById("shouldMapDivWithHeading");
        //when
        Appendix actualAppendix = divMapper.mapElement(element);
        //then
        try {
            List<ContentNode<String>> contentNodes = (List<ContentNode<String>>) actualAppendix.getLastContentNode().getContent();
            assertAll(
                    () -> assertEquals(1, actualAppendix.getContents().size()),
                    () -> assertEquals(ContentNodeType.DIV, actualAppendix.getLastContentNode().getContentNodeType()),
                    () -> assertEquals(4, contentNodes.size()),
                    () -> assertEquals("Description", actualAppendix.getTitle()),
                    () -> assertEquals("Sample Text", contentNodes.get(0).getContent())

            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void shouldMapDivWithMultipleHeadings() {
        //given
        Element element = sampleDivs.getElementById("shouldMapDivWithMultipleHeadings");
        //when
        Appendix actualAppendix = divMapper.mapElement(element);
        //then
        try {
            List<ContentNode<String>> contentNodes = (List<ContentNode<String>>) actualAppendix.getLastContentNode().getContent();
            assertAll(
                    () -> assertEquals(1, actualAppendix.getContents().size()),
                    () -> assertEquals(ContentNodeType.DIV, actualAppendix.getLastContentNode().getContentNodeType()),
                    () -> assertEquals(6, contentNodes.size()),
                    () -> assertEquals("Description", actualAppendix.getTitle()),
                    () -> assertEquals("Experiment", contentNodes.get(0).getContent())

            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void shouldMapOnlyImage() {
        //given
        Element element = sampleDivs.getElementById("shouldMapOnlyImage");
        //when
        Appendix actualAppendix = divMapper.mapElement(element);
        //then
        try {
            assertAll(
                    () -> assertEquals(1, actualAppendix.getContents().size()),
                    () -> assertEquals(ContentNodeType.IMAGE, actualAppendix.getLastContentNode().getContentNodeType()),
                    () -> assertFalse(actualAppendix.hasTitle())

            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void shouldMapOnlyImages() {
        //given
        Element element = sampleDivs.getElementById("shouldMapOnlyImages");
        //when
        Appendix actualAppendix = divMapper.mapElement(element);
        //then
        try {
            List<ContentNode<?>> contentNodes = (List<ContentNode<?>>) actualAppendix.getLastContentNode().getContent();
            assertAll(
                    () -> assertEquals(3, contentNodes.size()),
                    () -> assertTrue(contentNodes.stream().allMatch(contentNode -> contentNode.getContentNodeType().equals(ContentNodeType.IMAGE))),
                    () -> assertFalse(actualAppendix.hasTitle())
            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void shouldMapDivInsideDiv() {
        //given
        Element element = sampleDivs.getElementById("shouldMapDivInsideDiv");
        //when
        Appendix actualAppendix = divMapper.mapElement(element);
        //then
        try {
            List<ContentNode<?>> contentNodes = (List<ContentNode<?>>) actualAppendix.getLastContentNode().getContent();
            List<ContentNode<?>> innerContentNodes = (List<ContentNode<?>>) contentNodes.get(4).getContent();
            assertAll(
                    () -> assertEquals(5, contentNodes.size()),
                    () -> assertEquals(ContentNodeType.DIV, contentNodes.get(4).getContentNodeType()),
                    () -> assertEquals(6, innerContentNodes.size()),
                    () -> assertTrue(actualAppendix.hasTitle())
            );
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void shouldMapAnomBar() {
        //given
        Element anomBarElement = sampleDivs.getElementById("shouldMapAnomBar");
        //when
        Appendix actualAppendix = divMapper.mapElement(anomBarElement);
        //then
        assertEquals(1, actualAppendix.getContents().size());
        try {
            ContentNode<List<Appendix>> appendicesContentNode = (ContentNode<List<Appendix>>) actualAppendix.getContents().get(0);

            List<Appendix> appendices = appendicesContentNode.getContent();
            assertAll(
                    () -> assertEquals(5, appendicesContentNode.getContent().size()),
                    () -> assertAll(
                            () -> assertEquals("Item #", appendices.get(0).getTitle()),
                            () -> assertEquals(1, appendices.get(0).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(0).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("SCP-5004", appendices.get(0).getContents().get(0).getContent())
                    ),
                    () -> assertAll(
                            () -> assertEquals("Object Class", appendices.get(1).getTitle()),
                            () -> assertEquals(1, appendices.get(1).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(1).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("Esoteric", appendices.get(1).getContents().get(0).getContent())
                    ),
                    () -> assertAll(
                            () -> assertEquals("Secondary Class", appendices.get(2).getTitle()),
                            () -> assertEquals(1, appendices.get(2).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(2).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("Thaumiel", appendices.get(2).getContents().get(0).getContent())
                    ),
                    () -> assertAll(
                            () -> assertEquals("Disruption Class", appendices.get(3).getTitle()),
                            () -> assertEquals(1, appendices.get(3).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(3).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("Ekhi", appendices.get(3).getContents().get(0).getContent())
                    ),
                    () -> assertAll(
                            () -> assertEquals("Risk Class", appendices.get(4).getTitle()),
                            () -> assertEquals(1, appendices.get(4).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(4).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("Notice", appendices.get(4).getContents().get(0).getContent()
                    )));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void shouldMapAnomBar2() {
        //given
        Element anomBarElement = sampleDivs.getElementById("shouldMapAnomBar2");
        //when
        Appendix actualAppendix = divMapper.mapElement(anomBarElement);
        //then
        assertEquals(1, actualAppendix.getContents().size());
        try {
            ContentNode<List<Appendix>> appendicesContentNode = (ContentNode<List<Appendix>>) actualAppendix.getContents().get(0);

            List<Appendix> appendices = appendicesContentNode.getContent();
            assertAll(
                    () -> assertEquals(4, appendicesContentNode.getContent().size()),
                    () -> assertAll(
                            () -> assertEquals("Item #", appendices.get(0).getTitle()),
                            () -> assertEquals(1, appendices.get(0).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(0).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("SCP-2105", appendices.get(0).getContents().get(0).getContent())
                    ),
                    () -> assertAll(
                            () -> assertEquals("Object Class", appendices.get(1).getTitle()),
                            () -> assertEquals(1, appendices.get(1).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(1).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("Euclid", appendices.get(1).getContents().get(0).getContent())
                    ),
                    () -> assertAll(
                            () -> assertEquals("Disruption Class", appendices.get(2).getTitle()),
                            () -> assertEquals(1, appendices.get(2).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(2).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("Vlam", appendices.get(2).getContents().get(0).getContent())
                    ),
                    () -> assertAll(
                            () -> assertEquals("Risk Class", appendices.get(3).getTitle()),
                            () -> assertEquals(1, appendices.get(3).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(3).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("Notice", appendices.get(3).getContents().get(0).getContent()
                            )));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void shouldMapAnomBar3() {
        //given
        Element anomBarElement = sampleDivs.getElementById("shouldMapAnomBar3");
        //when
        Appendix actualAppendix = divMapper.mapElement(anomBarElement);
        //then
        assertEquals(1, actualAppendix.getContents().size());
        try {
            ContentNode<List<Appendix>> appendicesContentNode = (ContentNode<List<Appendix>>) actualAppendix.getContents().get(0);

            List<Appendix> appendices = appendicesContentNode.getContent();
            assertAll(
                    () -> assertEquals(2, appendicesContentNode.getContent().size()),
                    () -> assertAll(
                            () -> assertEquals("Item #", appendices.get(0).getTitle()),
                            () -> assertEquals(1, appendices.get(0).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(0).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("SCP-4511", appendices.get(0).getContents().get(0).getContent())
                    ),
                    () -> assertAll(
                            () -> assertEquals("Object Class", appendices.get(1).getTitle()),
                            () -> assertEquals(1, appendices.get(1).getContents().size()),
                            () -> assertEquals(ContentNodeType.TEXT, appendices.get(1).getContents().get(0).getContentNodeType()),
                            () -> assertEquals("Pending", appendices.get(1).getContents().get(0).getContent())
                    ));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

}