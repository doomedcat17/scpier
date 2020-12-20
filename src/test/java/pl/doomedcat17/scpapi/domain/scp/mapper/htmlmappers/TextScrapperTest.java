package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.ContentNode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextScrapperTest {

    private TextScrapper textScrapper = new TextScrapper();

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/lines/SampleLinesElements.html");

    @Test
    void shouldMapSimpleParagraph() {
        //given
        Element paragraphElement = sampleLines.getElementById("sampleLine");
        //when
        List<ContentNode<String>> contentNodes = textScrapper.scrapText(paragraphElement);
        //then
        assertEquals(paragraphElement.text(), contentNodes.get(0).getContent());

    }
    @Test
    void shouldMapSimpleParagraphWithStrongElement() {
        //given
        Element paragraphElement = sampleLines.getElementById("sampleLineWithHeader");
        //when
        List<ContentNode<String>> contentNodes = textScrapper.scrapText(paragraphElement);
        //then
        assertEquals(paragraphElement.text(), contentNodes.get(0).getContent());

    }

    @Test
    void shouldScrapSimpleTextWithDeletedContent() {
        //given
        Element element = sampleLines.getElementById("sampleLineWithDeletedContent");
        //when
        List<ContentNode<String>> output = textScrapper.scrapText(element);
        //then
        assertAll(() -> assertEquals(3, output.size()),
                () -> assertEquals(element.selectFirst("span").text(), output.get(1).getContent()));

    }

    @Test
    void shouldScrapDeletedContent() {
        //given
        Element element = sampleLines.getElementById("shouldScrapDeletedContent");
        //when
        List<ContentNode<String>> output = textScrapper.scrapText(element);
        //then
        assertAll(() -> assertEquals(1, output.size()),
                () -> assertEquals(element.text(), output.get(0).getContent()));

    }

    @Test
    void shouldScrapDeletedContent2() {
        //given
        Element element = sampleLines.getElementById("shouldScrapDeletedContent2");
        //when
        List<ContentNode<String>> output = textScrapper.scrapText(element);
        //then
        assertAll(() -> assertEquals(1, output.size()),
                () -> assertEquals(element.text(), output.get(0).getContent()));
    }

    @Test
    void shouldScrapStrongElement() {
        //given
        Element element = sampleLines.getElementById("sampleStrong");
        //when
        List<ContentNode<String>> output = textScrapper.scrapText(element);
        //then
        assertEquals(element.text(), output.get(0).getContent());
    }
    @Test
    void shouldScrapStrongWithSubElement() {
        //given
        Element element = sampleLines.getElementById("shouldScrapStrongWithSubElement");
        //when
        List<ContentNode<String>> output = textScrapper.scrapText(element);
        //then
        assertEquals("Sample text inside strong element[2]!", output.get(0).getContent());
    }

    @Test
    void shouldScrapStrongElementWithDeletedContent() {
        //given
        Element element = sampleLines.getElementById("sampleStrongWithDeletedElement");
        //when
        List<ContentNode<String>> output = textScrapper.scrapText(element);
        //then
        String text = output.get(0).getContent()+output.get(1).getContent()+output.get(2).getContent();
        assertEquals(element.text(), text);
    }

}