package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ParagraphNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.line.LineScrapper;
import com.doomedcat17.scpier.scrapper.line.TextNodeSplitter;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LineScrapperTest extends ScrapperTest {

    private final LineScrapper lineScrapper = new LineScrapper(SOURCE);

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/testdata/lines/SampleLinesElements.html");

    private final Map<String, ContentNode<?>> expectedOutputs = getExpectedContentNodeOutputs("src/test/resources/html/testdata/lines/expected_outputs.json");

    @Test
    void shouldScrapSimpleLine()  {
        //given
        Element simpleLine = sampleLines.getElementById("sampleLine");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(simpleLine);
        //then
        assertEquals(expectedOutputs.get("shouldMapSimpleLine"), contentNode);
    }

    @Test
    void shouldScrapSimpleLineWithDeletedContent()  {
        //given
        Element simpleLine = sampleLines.getElementById("sampleLineWithDeletedContent");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(simpleLine);
        //then
        assertEquals(expectedOutputs.get("sampleLineWithDeletedContent"), contentNode);
    }

    @Test
    void shouldScrapLineWithHeader()  {
        //given
        Element lineWithHeader = sampleLines.getElementById("shouldScrapLineWithHeader");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(lineWithHeader);
        //then
        assertEquals(expectedOutputs.get("shouldScrapLineWithHeader"), contentNode);

    }
    @Test
    void shouldScrapLineWithStrongElements()  {
        //given
        Element line = sampleLines.getElementById("shouldScrapContentWithStrongElements");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapContentWithStrongElements"), contentNode));

    }

    @Test
    void shouldScrapLineWithEmAndIElements()  {
        //given
        Element line = sampleLines.getElementById("shouldScrapContentWithEmAndIElements");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapContentWithEmAndIElements"), contentNode));

    }

    @Test
    void shouldScrapLineWithInnerSpanElements()  {
        //given
        Element line = sampleLines.getElementById("shouldScrapContentWithInnerSpanElements");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapContentWithInnerSpanElements"), contentNode));

    }

    @Test
    void shouldScrapLineWithStylization()  {
        //given
        Element line = sampleLines.getElementById("shouldScrapContentWithStylization");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(line);
        //then
        assertEquals(expectedOutputs.get("shouldScrapContentWithStylization"), contentNode);

    }
    @Test
    void shouldScrapStrongElement()  {
        //given
        Element line = sampleLines.getElementById("sampleStrong");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapStrongElement"), contentNode));
    }

    @Test
    void shouldScrapStrongElementAsTextNode()  {
        //given
        Element line = sampleLines.getElementById("sampleStrong2");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapStrongElementAsTextNode"), contentNode));
    }

    @Test
    void shouldScrapLineWithMultipleStrongElements()  {
        //given
        Element line = sampleLines.getElementById("shouldScrapLineWithMultipleStrongElements");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapLineWithMultipleStrongElements"), contentNode));
    }

    @Test
    void shouldScrapLine1()  {
        //given
        Element line = sampleLines.getElementById("shouldScrapLine1");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapLine1"), contentNode));
    }

    @Test
    void shouldScrapLine2()  {
        //given
        Element line = sampleLines.getElementById("shouldScrapLine2");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(line);
        //then
        assertAll(() -> assertEquals(expectedOutputs.get("shouldScrapLine2"), contentNode));
    }

    @Test
    void shouldScrapLine3()  {
        //given
        Element line = sampleLines.getElementById("shouldScrapLine3");
        //when
        ContentNode<?> contentNode = lineScrapper.scrapElement(line);
        //then
        assertEquals(expectedOutputs.get("shouldScrapLine3"), contentNode);
    }

    @Test
    void shouldSplitIntoParagraphs() {
        TextNodeSplitter textNodeSplitter = new TextNodeSplitter();
        //given
        List<TextNode> textNodes = new ArrayList<>();
        textNodes.add(new TextNode("paragraph\nparagraph\nparagraph"));
        //when
        List<ParagraphNode> paragraphs = textNodeSplitter.splitIntoParagraphs(textNodes);
        //then
        for (ContentNode<List<TextNode>> paragraph: paragraphs) {
            if (paragraph.getContent().size() == 1) {
                if (!paragraph.getContent().get(0).getContent().equals("paragraph")) fail();
            } else fail();
        }
        assertTrue(true);

    }


}