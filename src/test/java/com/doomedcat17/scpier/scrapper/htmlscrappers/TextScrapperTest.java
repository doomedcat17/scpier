package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.text.TextScrapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextScrapperTest extends ScrapperTest {

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/testdata/text/sampleTextElements.html");

    private final Map<String, List<TextNode>> expectedOutputs = getListOfExpectedTextNodes("src/test/resources/html/testdata/text/expected_outputs.json");

    private boolean compareStyles(Map<String, String> first, Map<String, String> second) {
        return first.entrySet().stream()
                .allMatch(e -> e.getValue().equals(second.get(e.getKey())));
    }
    private boolean areTextNodesStylesAreEqual(List<TextNode> firstList, List<TextNode> secondList) {
        if (firstList.size() != secondList.size()) return false;
        for (int i = 0; i < firstList.size(); i++) {
            if (!compareStyles(firstList.get(i).getStyles(), secondList.get(i).getStyles())) return false;
        }
        return true;
    }

    @Test
    void shouldScrapSimpleText() {
        //given
        Element textElement = sampleLines.getElementById("shouldScrapSimpleText");
        //when
        List<TextNode> contentNodes = TextScrapper.scrapText(textElement, SOURCE);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleText"), contentNodes);
        assertTrue(areTextNodesStylesAreEqual(expectedOutputs.get("shouldScrapSimpleText"), contentNodes));
    }

    @Test
    void shouldScrapSimpleText2()  {
        //given
        Element textElement = sampleLines.getElementById("shouldScrapSimpleText2");
        //when
        List<TextNode> contentNodes = TextScrapper.scrapText(textElement, SOURCE);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleText2"), contentNodes);
        assertTrue(areTextNodesStylesAreEqual(expectedOutputs.get("shouldScrapSimpleText2"), contentNodes));
    }

    @Test
    void shouldScrapTextWithStyling1()  {
        //given
        Element textElement = sampleLines.getElementById("shouldScrapTextWithStyling1");
        //when
        List<TextNode> contentNodes = TextScrapper.scrapText(textElement, SOURCE);
        //then
        assertEquals(expectedOutputs.get("shouldScrapTextWithStyling1"), contentNodes);
        assertTrue(areTextNodesStylesAreEqual(expectedOutputs.get("shouldScrapTextWithStyling1"), contentNodes));

    }

    @Test
    void shouldScrapTextWithStyling2() {
        //given
        Element textElement = sampleLines.getElementById("shouldScrapTextWithStyling2");
        //when
        List<TextNode> contentNodes = TextScrapper.scrapText(textElement, SOURCE);
        //then
        assertEquals(expectedOutputs.get("shouldScrapTextWithStyling2"), contentNodes);
        assertTrue(areTextNodesStylesAreEqual(expectedOutputs.get("shouldScrapTextWithStyling2"), contentNodes));

    }

    @Test
    void shouldScrapTextWithStyling3() {
        //given
        Element textElement = sampleLines.getElementById("shouldScrapTextWithStyling3");
        //when
        List<TextNode> contentNodes = TextScrapper.scrapText(textElement, SOURCE);
        //then
        assertEquals(expectedOutputs.get("shouldScrapTextWithStyling3"), contentNodes);
        assertTrue(areTextNodesStylesAreEqual(expectedOutputs.get("shouldScrapTextWithStyling3"), contentNodes));
    }

    @Test
    void shouldScrapTextWithStyling4()  {
        //given
        Element textElement = sampleLines.getElementById("shouldScrapTextWithStyling4");
        //when
        List<TextNode> contentNodes = TextScrapper.scrapText(textElement, SOURCE);
        //then
        assertEquals(expectedOutputs.get("shouldScrapTextWithStyling4"), contentNodes);
        assertTrue(areTextNodesStylesAreEqual(expectedOutputs.get("shouldScrapTextWithStyling4"), contentNodes));
    }

    @Test
    void shouldScrapTextWithStyling5()  {
        //given
        Element textElement = sampleLines.getElementById("shouldScrapTextWithStyling5");
        //when
        List<TextNode> contentNodes = TextScrapper.scrapText(textElement, SOURCE);
        //then
        assertEquals(expectedOutputs.get("shouldScrapTextWithStyling5"), contentNodes);
        assertTrue(areTextNodesStylesAreEqual(expectedOutputs.get("shouldScrapTextWithStyling5"), contentNodes));
    }

    @Test
    void shouldScrapTextWithStyling6()  {
        //given
        Element textElement = sampleLines.getElementById("shouldScrapTextWithStyling6");
        //when
        List<TextNode> contentNodes = TextScrapper.scrapText(textElement, SOURCE);
        //then
        assertEquals(expectedOutputs.get("shouldScrapTextWithStyling6"), contentNodes);
        assertTrue(areTextNodesStylesAreEqual(expectedOutputs.get("shouldScrapTextWithStyling6"), contentNodes));
    }

}