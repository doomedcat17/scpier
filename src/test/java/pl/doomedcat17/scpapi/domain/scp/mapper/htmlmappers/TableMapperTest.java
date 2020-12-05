package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.data.Appendix;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableMapperTest {
/*
    private TableMapper tableMapper = new TableMapper();

    private List<Appendix<?>> appendices;

    @BeforeEach
    void init() {
        appendices = new ArrayList<>();
    }

    @Test
    void shouldMapSimpleTable() {
        Element simpleTable = Jsoup.parse("<table class=\"wiki-content-table\">\n" +
                "<tbody><tr>\n" +
                "<th>Toy/Game</th>\n" +
                "<th>Usage by Child</th>\n" +
                "<th>Result</th>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>Red and white striped rubber ball with a gold star on one end</td>\n" +
                "<td>8-year-old male bounced against a wall 37 times prior to throwing it at a supervising junior researcher.</td>\n" +
                "<td>The wall had noticeable shallow dents where it had been struck. The junior researcher suffered 2 cracked ribs and significant soft tissue bruising where she had been struck. Child expressed disappointment that junior researcher didn't throw the ball back.</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>Stuffed elephant made of felt, measuring 35&nbsp;cm in height, wearing a red and gold saddle with the initials \"HF\" embroidered on the sides</td>\n" +
                "<td>4-year-old female moved toy as if it were walking, child making trumpeting noises before making it step on the foot of supervisory D-class.</td>\n" +
                "<td>D-class's foot suffered multiple complex bone fractures and hemorrhaging consistent with a crush injury. Child chided D-class for getting in the way of the toy.</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>\"See The Big Top!\", a board game of similar design to the 2004 edition of \"Candyland\"</td>\n" +
                "<td>6-year-old male begged supervisory D-class to play game until D-class was ordered to do so by researchers.</td>\n" +
                "<td>Child lost game and threw cards at D-class in anger. D-class suffered deep paper cuts to the face, hands, and forearms, requiring multiple bandages. Child hugged D-class after completion of game and asked if she would receive Batman adhesive bandages to \"make the boo-boos better.\"</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>Tin container labeled \"Junior Clown Kit!\" containing 30&nbsp;g (1 oz.) of clown white greasepaint, 2 red jumbo makeup pencils, 2 yellow/gold makeup pencils, small hand-mirror</td>\n" +
                "<td>7-year-old female decorated own face and that of supervisory D-class</td>\n" +
                "<td>D-class suffered mild chemical burns where makeup had made contact with skin and developed persistent allergy to lanolin. Child was unharmed.</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>Lacquer-finished red wooden rod resembling a miniature version of <a href=\"/scp-2024\">SCP-2024</a></td>\n" +
                "<td>9-year-old female touched various furnishings around the room, including the supervising D-class's arm.</td>\n" +
                "<td>D-class's arm tied into a knot. Child commented on D-class's improved physical appearance.</td>\n" +
                "</tr>\n" +
                "</tbody></table>","", Parser.xmlParser())
                .selectFirst("table");
        String expectedContent = "• Enhanced survivability in the bacterium's natural environment and similar environments;\n" +
                "• Full spectrum antibiotic resistance;\n" +
                "• Increased reproduction rate and consumption of available material;\n" +
                "• Development of a sporulation ability in gram-positive bacteria;\n" +
                "• Increased ability to uptake, hold, and share plasmids, particularly in gram-negative bacteria;\n" +
                "• Increased transmission, due to traits described above.";
        //when
        tableMapper.mapElement(simpleTable, appendices);
        //then
        System.out.println(appendices.get(0).getContent());

    }

 */

}