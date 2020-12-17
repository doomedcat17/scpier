package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HtmlMapperTest extends HtmlMapper {

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/lines/SampleLinesElements.html");

    @Test
    void shouldScrapSimpleText() {
        //given
        Element element = sampleLines.getElementById("sampleLine");
        //when
        List<ContentNode<String>> output = scrapText(element);
        //then
        assertAll(() -> assertEquals(1, output.size()),
                () -> assertEquals(element.text(), output.get(0).getContent()));

    }

    @Test
    void shouldScrapTextWithStrongElements() {
        //given
        Element element = sampleLines.getElementById("sampleLineWithHeader");
        //when
        List<ContentNode<String>> output = scrapText(element);
        //then
        assertAll(() -> assertEquals(1, output.size()),
                () -> assertEquals(element.text(), output.get(0).getContent()));

    }

    @Test
    void shouldScrapSimpleTextWithDeletedContent() {
        //given
        Element element = sampleLines.getElementById("sampleLineWithDeletedContent");
        //when
        List<ContentNode<String>> output = scrapText(element);
        //then
        assertAll(() -> assertEquals(3, output.size()),
                () -> assertEquals(element.selectFirst("span").text(), output.get(1).getContent()));

    }

    @Test
    void shouldScrapDeletedContent() {
        //given
        Element element = sampleLines.getElementById("shouldScrapDeletedContent");
        //when
        List<ContentNode<String>> output = scrapText(element);
        //then
        assertAll(() -> assertEquals(1, output.size()),
                () -> assertEquals(element.text(), output.get(0).getContent()));

    }

    @Test
    void shouldScrapDeletedContent2() {
        //given
        Element element = sampleLines.getElementById("shouldScrapDeletedContent2");
        //when
        List<ContentNode<String>> output = scrapText(element);
        //then
        assertAll(() -> assertEquals(1, output.size()),
                () -> assertEquals(element.text(), output.get(0).getContent()));
    }


    @Override
    public Appendix mapElement(Element element) {
        return null;
    }
}