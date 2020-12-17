package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;


import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.Appendix;
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
        // TODO fix bug
        //given
        Element table = sampleTables.getElementById("shouldMapSimpleTableWithStylization");
        //when
        Appendix actualAppendix = tableMapper.mapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleTableWithStylization").get(0), actualAppendix);
    }

}