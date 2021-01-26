package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;


import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TableMapperTest {

    private TableMapper tableMapper = new TableMapper();

    private final Element sampleTables = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/tables/SampleTableElements.html");

    private final Map<String, List<Appendix>> expectedOutputs = TestDataProvider
            .getExpectedAppendicesOutputs("src/test/resources/html/test_data/tables/expected_outputs.json");

    @Test
    void shouldMapSimpleTable() {
        //given
        Element table = sampleTables.getElementById("shouldMapSimpleTable");
        //when
        Appendix actualAppendix = tableMapper.mapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleTable").get(0), actualAppendix);
    }

    @Test
    void shouldMapSimpleTableWithoutTbody() {
        //given
        Element table = sampleTables.getElementById("shouldMapSimpleTableWithoutTbody");
        //when
        Appendix actualAppendix = tableMapper.mapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleTable").get(0), actualAppendix);
    }
    @Test
    void shouldMapSimpleTable2() {
        //given
        Element table = sampleTables.getElementById("shouldMapSimpleTable2");
        //when
        Appendix actualAppendix = tableMapper.mapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleTable2").get(0), actualAppendix);
    }

    @Test
    void shouldMapSimpleTableWithStylization() {
        //given
        Element table = sampleTables.getElementById("shouldMapSimpleTableWithStylization");
        //when
        Appendix actualAppendix = tableMapper.mapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleTableWithStylization").get(0), actualAppendix);
    }

    @Test
    void shouldMapEnBaseTable() {
        //given
        Element table = sampleTables.getElementById("shouldMapEnBaseTable");
        //when
        Appendix actualAppendix = tableMapper.mapElement(table);
        //then
        assertAll(
                () -> assertEquals(1, actualAppendix.getContents().size()),
                () -> assertEquals(ContentNodeType.APPENDICES, actualAppendix.getContents().get(0).getContentNodeType())
        );
        List<Appendix> appendices = (List<Appendix>) actualAppendix.getContents()
                .get(0)
                .getContent();
        assertAll(
                () -> assertEquals("Item #", appendices.get(0).getTitle()),
                () -> assertEquals("Object Class", appendices.get(1).getTitle()),
                () -> assertEquals(new ContentNode<>(ContentNodeType.TEXT, "SCP-2021"), appendices.get(0).getContents().get(0)),
                () -> assertEquals(new ContentNode<>(ContentNodeType.TEXT, "Safe"), appendices.get(1).getContents().get(0))
        );

    }

    @Test
    void shouldMapEnBaseTable2() {
        //given
        Element table = sampleTables.getElementById("shouldMapEnBaseTable2");
        //when
        Appendix actualAppendix = tableMapper.mapElement(table);
        //then
        assertAll(
                () -> assertEquals(1, actualAppendix.getContents().size()),
                () -> assertEquals(ContentNodeType.APPENDICES, actualAppendix.getContents().get(0).getContentNodeType())
        );
        List<Appendix> appendices = (List<Appendix>) actualAppendix.getContents()
                .get(0)
                .getContent();
        assertAll(
                () -> assertEquals("Item #", appendices.get(0).getTitle()),
                () -> assertEquals("Object Class", appendices.get(1).getTitle()),
                () -> assertEquals(new ContentNode<>(ContentNodeType.TEXT, "SCP-1934"), appendices.get(0).getContents().get(0)),
                () -> assertEquals(new ContentNode<>(ContentNodeType.TEXT, "Safe"), appendices.get(1).getContents().get(0))
        );

    }

    @Test
    void shouldMapEnBaseTable3() {
        //given
        Element table = sampleTables.getElementById("shouldMapEnBaseTable3");
        //when
        Appendix actualAppendix = tableMapper.mapElement(table);
        //then
        assertAll(
                () -> assertEquals(1, actualAppendix.getContents().size()),
                () -> assertEquals(ContentNodeType.APPENDICES, actualAppendix.getContents().get(0).getContentNodeType())
        );
        List<Appendix> appendices = (List<Appendix>) actualAppendix.getContents()
                .get(0)
                .getContent();
        assertAll(
                () -> assertEquals("Item #", appendices.get(0).getTitle()),
                () -> assertEquals("Object Class", appendices.get(1).getTitle()),
                () -> assertEquals(new ContentNode<>(ContentNodeType.TEXT, "SCP-2058"), appendices.get(0).getContents().get(0)),
                () -> assertEquals(new ContentNode<>(ContentNodeType.TEXT, "Safe"), appendices.get(1).getContents().get(0))
        );

    }

}