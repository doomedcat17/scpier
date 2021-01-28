package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.TextNode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextScrapperRefactorTest {

    private TextScrapperRefactor textScrapper = new TextScrapperRefactor();

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/lines/SampleLinesElements.html");

    @Test
    void shouldMapSimpleParagraph() throws JsonProcessingException {
        //given
        Element paragraphElement = sampleLines.getElementById("sampleLineWithHeader");
        //when
        List<TextNode> textNodes = textScrapper.scrapText(paragraphElement);
        //then
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(textNodes));

    }

}