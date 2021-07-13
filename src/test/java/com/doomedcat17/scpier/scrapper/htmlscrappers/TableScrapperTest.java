package com.doomedcat17.scpier.scrapper.htmlscrappers;


import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.table.TableScrapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TableScrapperTest extends ScrapperTest {

    private final TableScrapper tableMapper = new TableScrapper(SOURCE);

    private final Element sampleTables = TestDataProvider
            .getSampleElements("src/test/resources/html/testdata/tables/SampleTableElements.html");

    private final Map<String, ContentNode<?>> expectedOutputs =
            getExpectedAppendicesOutputs("src/test/resources/html/testdata/tables/expected_outputs.json");

    @Test
    void shouldScrapSimpleTable() throws ElementScrapperException {
        //given
        Element table = sampleTables.getElementById("shouldScrapSimpleTable");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleTable"), contentNode);
    }

    @Test
    void shouldScrapSimpleTableWithoutTbody() throws ElementScrapperException {
        //given
        Element table = sampleTables.getElementById("shouldScrapSimpleTableWithoutTbody");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleTable"), contentNode);
    }
    @Test
    void shouldScrapSimpleTable2() throws ElementScrapperException {
        //given
        Element table = sampleTables.getElementById("shouldScrapSimpleTable2");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleTable2"), contentNode);
    }

    @Test
    void shouldScrapEnBaseTable() throws ElementScrapperException {
        //given
        Element table = sampleTables.getElementById("shouldMapEnBaseTable");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertEquals(ContentNodeType.PARAGRAPHS, contentNode.getContentNodeType());
        ContentNode<List<ContentNode<List<TextNode>>>> paragraphs = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;
        assertEquals("Item #: ", paragraphs.getContent().get(0).getContent().get(0).getContent());
        assertEquals("SCP-2021", paragraphs.getContent().get(0).getContent().get(1).getContent());
        assertEquals("Object Class: ", paragraphs.getContent().get(1).getContent().get(0).getContent());
        assertEquals("Safe", paragraphs.getContent().get(1).getContent().get(1).getContent());

    }

    @Test
    void shouldMapEnBaseTable2() throws ElementScrapperException {
        //given
        Element table = sampleTables.getElementById("shouldMapEnBaseTable2");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertEquals(ContentNodeType.PARAGRAPHS, contentNode.getContentNodeType());
        ContentNode<List<ContentNode<List<TextNode>>>> paragraphs = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;
        assertEquals("Item #: ", paragraphs.getContent().get(0).getContent().get(0).getContent());
        assertEquals("SCP-1934", paragraphs.getContent().get(0).getContent().get(1).getContent());
        assertEquals("Object Class: ", paragraphs.getContent().get(1).getContent().get(0).getContent());
        assertEquals("Safe", paragraphs.getContent().get(1).getContent().get(1).getContent());

    }

    @Test
    void shouldMapEnBaseTable3() throws ElementScrapperException {
        //given
        Element table = sampleTables.getElementById("shouldMapEnBaseTable3");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertEquals(ContentNodeType.PARAGRAPHS, contentNode.getContentNodeType());
        ContentNode<List<ContentNode<List<TextNode>>>> paragraphs = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;
        assertEquals("Item #: ", paragraphs.getContent().get(0).getContent().get(0).getContent());
        assertEquals("SCP-2058", paragraphs.getContent().get(0).getContent().get(1).getContent());
        assertEquals("Object Class: ", paragraphs.getContent().get(1).getContent().get(0).getContent());
        assertEquals("Safe", paragraphs.getContent().get(1).getContent().get(1).getContent());

    }


}