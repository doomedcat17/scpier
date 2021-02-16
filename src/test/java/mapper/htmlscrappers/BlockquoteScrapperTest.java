package mapper.htmlscrappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import data.appendencies.Appendix;
import mapper.htmlscrappers.blockquote.BlockquoteScrapper;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BlockquoteScrapperTest extends ScrapperTest {


    private BlockquoteScrapper blockquoteScrapper = new BlockquoteScrapper();

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/blockquotes/SampleBlockquoteElements.html");

    private final Map<String, List<Appendix>> expectedOutputs =
            getExpectedAppendicesOutputs("src/test/resources/html/test_data/blockquotes/expected_outputs.json");

    @Test
    void shouldScrapSimpleBlockquote() {
        //given
        Element element = sampleLines.getElementById("shouldScrapSimpleBlockquote");
        //when
        Appendix actualAppendix = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapSimpleBlockquote").get(0), actualAppendix);
    }

    @Test
    void shouldScrapBlockquoteWithLongLine() {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithLongLine");
        //when
        Appendix actualAppendix = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithLongLine").get(0), actualAppendix);
    }
    @Test
    void shouldScrapBlockquoteWithMultipleLines() {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithMultipleLines");
        //when
        Appendix actualAppendix = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithMultipleLines").get(0), actualAppendix);
    }
    @Test
    void shouldScrapBlockquoteWithDeletedContent() {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithDeletedContent");
        //when
        Appendix actualAppendix = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithDeletedContent").get(0), actualAppendix);
    }
    @Test
    void shouldScrapBlockquoteWithList() {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithList");
        //when
        Appendix actualAppendix = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithList").get(0), actualAppendix);
    }

    @Test
    void shouldScrapBlockquoteWithHeading() {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithHeading");
        //when
        Appendix actualAppendix = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithHeading").get(0), actualAppendix);
    }

    @Test
    void shouldScrapBlockquoteWithHeadingAsTittle() {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithHeadingAsTittle");
        //when
        Appendix actualAppendix = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithHeadingAsTittle").get(0), actualAppendix);
    }

    @Test
    void shouldScrapBlockquoteWithImage() {
        //given
        Element element = sampleLines.getElementById("shouldScrapBlockquoteWithImage");
        //when
        Appendix actualAppendix = blockquoteScrapper.scrapElement(element);
        //then
        assertEquals(expectedOutputs.get("shouldScrapBlockquoteWithImage").get(0), actualAppendix);
    }
}