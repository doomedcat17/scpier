package com.doomedcat17.scpier.scrapper.htmlscrappers;


import com.doomedcat17.scpapi.TestDataProvider;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;
import com.doomedcat17.scpier.scrapper.htmlscrappers.table.TableScrapper;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TableScrapperTest extends ScrapperTest {

    private TableScrapper tableMapper = new TableScrapper(SOURCE, titleResolver);

    private final Element sampleTables = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/tables/SampleTableElements.html");

    private Map<String, List<Appendix>> expectedOutputs =
            getExpectedAppendicesOutputs("src/test/resources/html/test_data/tables/expected_outputs.json");

    @Test
    void shouldScrapSimpleTable() {
        //given
        Element table = sampleTables.getElementById("shouldScrapSimpleTable");
        //when
        Appendix actualAppendix = tableMapper.scrapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleTable").get(0), actualAppendix);
    }

    @Test
    void shouldScrapSimpleTableWithoutTbody() {
        //given
        Element table = sampleTables.getElementById("shouldScrapSimpleTableWithoutTbody");
        //when
        Appendix actualAppendix = tableMapper.scrapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleTable").get(0), actualAppendix);
    }
    @Test
    void shouldScrapSimpleTable2() {
        //given
        Element table = sampleTables.getElementById("shouldScrapSimpleTable2");
        //when
        Appendix actualAppendix = tableMapper.scrapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleTable2").get(0), actualAppendix);
    }

    @Test
    void shouldMapEnBaseTable() {
        //given
        Element table = sampleTables.getElementById("shouldMapEnBaseTable");
        //when
        Appendix actualAppendix = tableMapper.scrapElement(table);
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
        Appendix actualAppendix = tableMapper.scrapElement(table);
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
        Appendix actualAppendix = tableMapper.scrapElement(table);
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