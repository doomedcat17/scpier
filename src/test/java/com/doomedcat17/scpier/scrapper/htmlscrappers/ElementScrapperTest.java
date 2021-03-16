package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpapi.TestDataProvider;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.scp.scp_mappers.appendix_mappers.AppendixMapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import static org.junit.jupiter.api.Assertions.*;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import java.util.List;

class ElementScrapperTest extends ScrapperTest  {

    private final ElementScrapper elementScrapper = new DivScrapper(SOURCE, titleResolver);

    private final Element sampleDivs = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/divs/SampleDivsElements.html");

    @Test
    void shouldScrapInnerDivContent() {
        //given
        Element divElement = sampleDivs.getElementById("innerElementsScrapperTestDiv");
        //when
        List<ContentNode<?>> contentNodes = elementScrapper.scrapElementInnerContent(divElement);
        //then
        assertEquals(1, contentNodes.size());
        ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) contentNodes.get(0);
        assertEquals(divElement.selectFirst("code").text(), paragraph.getContent().get(0).getContent());
    }

    @Test
    void shouldScrapMath() {
        //given
        Element divElement = sampleDivs.getElementById("shouldScrapMath");
        //when
        List<ContentNode<?>> contentNodes = elementScrapper.scrapElementInnerContent(divElement);
        //then
        ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) contentNodes.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        paragraph.getContent().forEach(textNode -> stringBuilder.append(textNode.getContent()));
        assertEquals(divElement.text(), stringBuilder.toString());
    }

    @Test
    void shouldScrapMath2() {
        //given
        Element divElement = sampleDivs.getElementById("shouldScrapMath2");
        //when
        List<ContentNode<?>> contentNodes = elementScrapper.scrapElementInnerContent(divElement);
        //then
        ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) contentNodes.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        paragraph.getContent().forEach(textNode -> stringBuilder.append(textNode.getContent()));
        assertEquals(divElement.text(), stringBuilder.toString());
    }

    @Test
    void shouldScrapMaterialBox() {
        //given
        Element divElement = sampleDivs.getElementById("shouldScrapMaterialBox");
        //when
        List<Appendix> appendices = AppendixMapper.mapNodesToAppendices(divElement.childNodes(), SOURCE, titleResolver);
        //then
        try {
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appendices));
        } catch (Exception ignored) {}
    }




}