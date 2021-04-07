package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.text.TextScrapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TextScrapperTest extends ScrapperTest {

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/testdata/lines/SampleLinesElements.html");

    @Test
    void shouldMapSimpleParagraph() {
        //given
        Element paragraphElement = sampleLines.getElementById("sampleLine");
        //when
        List<TextNode> contentNodes = TextScrapper.scrapText(paragraphElement, SOURCE);
        //then
        assertEquals(paragraphElement.text(), contentNodes.get(0).getContent());

    }

    @Test
    void shouldMapSimpleParagraphWithStrongElement() {
        //given
        Element paragraphElement = sampleLines.getElementById("shouldScrapLineWithHeader");
        //when
        List<TextNode> contentNodes = TextScrapper.scrapText(paragraphElement, SOURCE);
        //then
        assertEquals(2, contentNodes.size());
        assertEquals(paragraphElement.selectFirst("strong").wholeText(), contentNodes.get(0).getContent());
        assertEquals(paragraphElement.childNode(1).toString(), contentNodes.get(1).getContent());

    }

    @Test
    void shouldScrapSimpleTextWithDeletedContent() {
        //given
        Element element = sampleLines.getElementById("sampleLineWithDeletedContent");
        //when
        List<TextNode> output = TextScrapper.scrapText(element, SOURCE);
        //then
        assertAll(() -> assertEquals(3, output.size()),
                () -> assertEquals(element.selectFirst("span").text(), output.get(1).getContent()));

    }

    @Test
    void shouldScrapDeletedContent() {
        //given
        Element element = sampleLines.getElementById("shouldScrapDeletedContent");
        //when
        List<TextNode> output = TextScrapper.scrapText(element, SOURCE);
        //then
        assertAll(() -> assertEquals(1, output.size()),
                () -> assertEquals(element.wholeText(), output.get(0).getContent()));
    }

    @Test
    void shouldScrapDeletedContent2() {
        //given
        Element element = sampleLines.getElementById("shouldScrapDeletedContent2");
        //when
        List<TextNode> output = TextScrapper.scrapText(element, SOURCE);
        //then
        assertAll(() -> assertEquals(1, output.size()),
                () -> assertEquals(element.wholeText(), output.get(0).getContent()));
    }

    @Test
    void shouldScrapStrongElement() {
        //given
        Element element = sampleLines.getElementById("sampleStrong");
        //when
        List<TextNode> output = TextScrapper.scrapText(element, SOURCE);
        //then
        assertEquals(element.wholeText(), output.get(0).getContent());
    }

    @Test
    void shouldScrapStrongWithSubElement() {
        //given
        Element element = sampleLines.getElementById("shouldScrapStrongWithSubElement");
        //when
        List<TextNode> output = TextScrapper.scrapText(element, SOURCE);
        //then
        assertAll(
                () -> assertEquals("Sample text inside strong element", output.get(0).getContent()),
                () -> assertEquals("2", output.get(1).getContent()),
                () -> assertEquals("!", output.get(2).getContent())
        );
    }

    @Test
    void shouldScrapSubElement() {
        //given
        Element element = sampleLines.getElementById("shouldScrapSubElement");
        //when
        List<TextNode> output = TextScrapper.scrapText(element, SOURCE);
        //then
        assertEquals("2", output.get(0).getContent());
    }

    @Test
    void shouldScrapStrongElementWithDeletedContent() {
        //given
        Element element = sampleLines.getElementById("sampleStrongWithDeletedElement");
        //when
        List<TextNode> output = TextScrapper.scrapText(element, SOURCE);
        //then
        String text = output.get(0).getContent() + output.get(1).getContent() + output.get(2).getContent();
        assertEquals(element.text(), text);
    }

}