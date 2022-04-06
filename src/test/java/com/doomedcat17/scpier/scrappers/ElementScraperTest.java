package com.doomedcat17.scpier.scrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementScraperTest extends ScrapperTest  {

    private final Element sampleDivs = TestDataProvider
            .getSampleElement("src/test/resources/html/test_data/divs/sample-divs-elements.html");

    @Test
    void shouldScrapMath()  {
        //given
        Element divElement = sampleDivs.getElementById("shouldScrapMath");
        //when
        List<ContentNode<?>> contentNodes = ElementContentScraper.scrapContent(divElement, SOURCE);
        //then
        ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) contentNodes.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        paragraph.getContent().forEach(textNode -> stringBuilder.append(textNode.getContent()));
        assertEquals(divElement.text(), stringBuilder.toString());
    }

    @Test
    void shouldScrapMath2()  {
        //given
        Element divElement = sampleDivs.getElementById("shouldScrapMath2");
        //when
        List<ContentNode<?>> contentNodes = ElementContentScraper.scrapContent(divElement, SOURCE);
        //then
        ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) contentNodes.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        paragraph.getContent().forEach(textNode -> stringBuilder.append(textNode.getContent()));
        assertEquals(divElement.text(), stringBuilder.toString());
    }
}