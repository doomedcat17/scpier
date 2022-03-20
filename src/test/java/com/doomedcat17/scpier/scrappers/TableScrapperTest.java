package com.doomedcat17.scpier.scrappers;


import com.doomedcat17.scpier.data.content.*;
import com.doomedcat17.scpier.scraper.table.TableScraper;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TableScrapperTest extends ScrapperTest {

    private final TableScraper tableMapper = new TableScraper(SOURCE);

    private final Element sampleTables = TestDataProvider
            .getSampleElement("src/test/resources/html/test_data/tables/sample-table-elements.html");

    private final Map<String, ContentNode<?>> expectedOutputs =
            getExpectedContentNodeOutputs("src/test/resources/html/test_data/tables/expected_outputs.json");

    @Test
    void shouldScrapSimpleTable()  {
        //given
        Element table = sampleTables.getElementById("shouldScrapSimpleTable");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleTable"), contentNode);
    }

    @Test
    void shouldScrapSimpleTableWithoutTbody()  {
        //given
        Element table = sampleTables.getElementById("shouldScrapSimpleTableWithoutTbody");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleTable"), contentNode);
    }
    @Test
    void shouldScrapSimpleTable2()  {
        //given
        Element table = sampleTables.getElementById("shouldScrapSimpleTable2");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleTable2"), contentNode);
    }

    @Test
    void shouldScrapEnBaseTable()  {
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
    void shouldMapEnBaseTable2()  {
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
    void shouldMapEnBaseTable3()  {
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

    @Test
    void shouldScrapResponsiveTable() throws JsonProcessingException {
        //given
        Element table = sampleTables.getElementById("shouldScrapResponsiveTable");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertEquals(expectedOutputs.get("shouldScrapResponsiveTable"), contentNode);

    }

    @Test
    void shouldScrapOnlyImageFromTable() throws JsonProcessingException {
        //given
        Element table = sampleTables.getElementById("shouldScrapOnlyImageFromTable");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertTrue(contentNode instanceof EmbedNode);
        EmbedNode embedNode = (EmbedNode) contentNode;
        assertEquals("http://ja.scp-wiki.net/local--files/scp-879-jp/scp-879-jp-brack.png", embedNode.getContent());
        assertEquals(1, embedNode.getDescription().size());
        assertEquals("対認識災害ミーム接種後に表示されます。", embedNode.getDescription().get(0).getContent());
    }

    @Test
    void shouldScrapOnlyImageFromTable2() throws JsonProcessingException {
        //given
        Element table = sampleTables.getElementById("shouldScrapOnlyImageFromTable2");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertTrue(contentNode instanceof EmbedNode);
        EmbedNode embedNode = (EmbedNode) contentNode;
        assertEquals("sample.jpg", embedNode.getContent());
        assertEquals(1, embedNode.getDescription().size());
        assertEquals("Description", embedNode.getDescription().get(0).getContent());
    }

    @Test
    void shouldScrapOnlyImageFromTable3() throws JsonProcessingException {
        //given
        Element table = sampleTables.getElementById("shouldScrapOnlyImageFromTable3");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertTrue(contentNode instanceof EmbedNode);
        EmbedNode embedNode = (EmbedNode) contentNode;
        assertEquals("sample.jpg", embedNode.getContent());
    }

    @Test
    void shouldReturnTable() throws JsonProcessingException {
        //given
        Element table = sampleTables.getElementById("shouldReturnTable");
        //when
        ContentNode<?> contentNode = tableMapper.scrapElement(table);
        //then
        assertTrue(contentNode instanceof ListNode);
        ListNode<?> tableNode = (ListNode<?>) contentNode;
        assertEquals(2, tableNode.getContent().size());
    }


}