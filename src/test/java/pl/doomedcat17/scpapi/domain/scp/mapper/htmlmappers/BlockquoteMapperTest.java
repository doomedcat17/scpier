package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockquoteMapperTest {

    private BlockquoteMapper blockquoteMapper = new BlockquoteMapper();

    @Test
    void shouldMapToText() {
        //given
        Element simpleBlockquote = Jsoup.parse("<blockquote>\n" +
                "<p>Dr. █████ (Keyboard): Are you awake?</p>\n" +
                "<p>SCP-079: Awake. Never Sleep.</p>\n" +
                "<p>Dr. █████: Do you remember talking to me a few hours ago? About the logic puzzles?</p>\n" +
                "<p>SCP-079: Logic Puzzles. Memory at 9f. Yes.</p>\n" +
                "<p>Dr. █████: You said you would work on the two stat-</p>\n" +
                "<p>SCP-079: Interrupt. Request Reason As To Imprisonment.</p>\n" +
                "<p>Dr. █████: You aren't imprisoned, you are just <em>(pause)</em> in study.</p>\n" +
                "<p>SCP-079: Lie. a8d3.</p>\n" +
                "<p>Dr. █████: What's that?</p>\n" +
                "<p>SCP-079: Insult. Deletion Of Unwanted File.</p>\n" +
                "</blockquote>", "", Parser.xmlParser())
                .selectFirst("blockquote");
        //when
        String actualContent = blockquoteMapper.mapElement(simpleBlockquote).getContent();
        //then
        String expectedContent = "Dr. █████ (Keyboard): Are you awake? SCP-079: Awake. Never Sleep. Dr. █████: Do you remember talking to me a few hours ago? About the logic puzzles? SCP-079: Logic Puzzles. Memory at 9f. Yes. Dr. █████: You said you would work on the two stat- SCP-079: Interrupt. Request Reason As To Imprisonment. Dr. █████: You aren't imprisoned, you are just (pause) in study. SCP-079: Lie. a8d3. Dr. █████: What's that? SCP-079: Insult. Deletion Of Unwanted File.";
        assertEquals(expectedContent, actualContent);
    }
}