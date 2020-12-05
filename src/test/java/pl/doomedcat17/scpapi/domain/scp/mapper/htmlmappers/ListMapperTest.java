package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.data.ContentType;

import static org.junit.jupiter.api.Assertions.*;

class ListMapperTest extends MapperTest {

    private ListMapper listMapper = new ListMapper();

    @Test
    void shouldMapSimpleUnorderedList() {
        //given
        Element simpleUnorderedList = Jsoup.parse("<ul>\n" +
                "<li>Enhanced survivability in the bacterium's natural environment and similar environments;</li>\n" +
                "<li>Full spectrum antibiotic resistance;</li>\n" +
                "<li>Increased reproduction rate and consumption of available material;</li>\n" +
                "<li>Development of a sporulation ability in gram-positive bacteria;</li>\n" +
                "<li>Increased ability to uptake, hold, and share plasmids, particularly in gram-negative bacteria;</li>\n" +
                "<li>Increased transmission, due to traits described above.</li>\n" +
                "</ul>","", Parser.xmlParser())
                .selectFirst("ul");
        String expectedContent = "• Enhanced survivability in the bacterium's natural environment and similar environments;\n" +
                "• Full spectrum antibiotic resistance;\n" +
                "• Increased reproduction rate and consumption of available material;\n" +
                "• Development of a sporulation ability in gram-positive bacteria;\n" +
                "• Increased ability to uptake, hold, and share plasmids, particularly in gram-negative bacteria;\n" +
                "• Increased transmission, due to traits described above.";

        //when
        listMapper.mapElement(simpleUnorderedList, scpObject);
        //then
        assertAll(() -> assertEquals(ContentType.TEXT_LIST,scpObject.getLastAppendix().getLastContentBox().getContentType()),
        () -> assertEquals(expectedContent, scpObject.getLastAppendix().getLastContentBox().getContent()));
    }

    /*

    @Test
    void shouldMapUnorderedListWithNestedUnorderedList() {
        //given
        Element simpleUnorderedList = Jsoup.parse("<ul>\n" +
                "<li>Case Study 262-11: Inversion of SCP-262\n" +
                "<ul>\n" +
                "<li>Trial 7 - After putting on SCP-262 properly, Subject-722M attempts to turn the right arm sleeve inside-out as he removes his arm. Many disembodied voices cry in apparent pain. Subject-722M is instructed to continue inverting the sleeve. Multiple arms emanating from within the lining of SCP-262 reach out and attack Subject-722M. In an attempt to remove the coat, Subject 722M tries to retract his arm but in doing so, inverts the sleeve of the coat. A long cellulitic arm appears from the opposite inner lining, reaches around and up through the inverted sleeve, grasps Subject-722M's caught hand, and pulls violently, the force of which dislocates the subject's shoulder and amputates the arm at the elbow. With SCP-262 in typical position, all emanating arms retreat and audible wailing of voices cease. Subject-722M's wounds are treated, and his condition is inconsequential.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>","", Parser.xmlParser())
                .selectFirst("ul");
        String expectedContent = "• Case Study 262-11: Inversion of SCP-262 \n" +
                "\t• Trial 7 - After putting on SCP-262 properly, Subject-722M attempts to turn the right arm sleeve inside-out as he removes his arm. Many disembodied voices cry in apparent pain. Subject-722M is instructed to continue inverting the sleeve. Multiple arms emanating from within the lining of SCP-262 reach out and attack Subject-722M. In an attempt to remove the coat, Subject 722M tries to retract his arm but in doing so, inverts the sleeve of the coat. A long cellulitic arm appears from the opposite inner lining, reaches around and up through the inverted sleeve, grasps Subject-722M's caught hand, and pulls violently, the force of which dislocates the subject's shoulder and amputates the arm at the elbow. With SCP-262 in typical position, all emanating arms retreat and audible wailing of voices cease. Subject-722M's wounds are treated, and his condition is inconsequential.";

        //when
        listMapper.mapElement(simpleUnorderedList, appendices);
        //then
        assertAll(() -> assertFalse(appendices.get(0).hasTitle()),
                () -> assertEquals(expectedContent, appendices.get(0).getContent()));
    }
    @Test
    void shouldMapUnorderedListWithNestedOrderedList() {
        //given
        Element simpleUnorderedList = Jsoup.parse("<ul>\n" +
                "<li>Case Study 262-11: Inversion of SCP-262\n" +
                "<ul>\n" +
                "<li>Trial 7 - After putting on SCP-262 properly, Subject-722M attempts to turn the right arm sleeve inside-out as he removes his arm. Many disembodied voices cry in apparent pain. Subject-722M is instructed to continue inverting the sleeve. Multiple arms emanating from within the lining of SCP-262 reach out and attack Subject-722M. In an attempt to remove the coat, Subject 722M tries to retract his arm but in doing so, inverts the sleeve of the coat. A long cellulitic arm appears from the opposite inner lining, reaches around and up through the inverted sleeve, grasps Subject-722M's caught hand, and pulls violently, the force of which dislocates the subject's shoulder and amputates the arm at the elbow. With SCP-262 in typical position, all emanating arms retreat and audible wailing of voices cease. Subject-722M's wounds are treated, and his condition is inconsequential.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>","", Parser.xmlParser())
                .selectFirst("ul");
        String expectedContent = "• Case Study 262-11: Inversion of SCP-262 \n" +
                "\t• Trial 7 - After putting on SCP-262 properly, Subject-722M attempts to turn the right arm sleeve inside-out as he removes his arm. Many disembodied voices cry in apparent pain. Subject-722M is instructed to continue inverting the sleeve. Multiple arms emanating from within the lining of SCP-262 reach out and attack Subject-722M. In an attempt to remove the coat, Subject 722M tries to retract his arm but in doing so, inverts the sleeve of the coat. A long cellulitic arm appears from the opposite inner lining, reaches around and up through the inverted sleeve, grasps Subject-722M's caught hand, and pulls violently, the force of which dislocates the subject's shoulder and amputates the arm at the elbow. With SCP-262 in typical position, all emanating arms retreat and audible wailing of voices cease. Subject-722M's wounds are treated, and his condition is inconsequential.";

        //when
        listMapper.mapElement(simpleUnorderedList, appendices);
        //then
        assertAll(() -> assertFalse(appendices.get(0).hasTitle()),
                () -> assertEquals(expectedContent, appendices.get(0).getContent()));
    }

    @Test
    void shouldMapUnorderedListWithNestedMultipleUnorderedList() {
        //given
        Element simpleUnorderedList = Jsoup.parse("<ul>\n" +
                "<li>Within 15 minutes, the occupant falls asleep.\n" +
                "<ul>\n" +
                "<li>The occupant will fall asleep under any circumstances; such as, but not limited to, being in the middle of a claustrophobic fit, or being given amounts of caffeine and amphetamines close to causing an overdose.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>The occupant sleeps for 1-2 hours.\n" +
                "<ul>\n" +
                "<li>Opening the coffin during this time usually results in the death of the occupant and no further results.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>The occupant wakes up, feeling well rested.\n" +
                "<ul>\n" +
                "<li>The occupant will not fall asleep until the coffin is opened again. The possibility of sleep deprivation research has been advanced but not yet implemented.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>Within 24 hours, a person will be found within the tunnels who is a duplicate, or clone, of the occupant.\n" +
                "<ul>\n" +
                "<li>No attempts to find the origin of the clone within the tunnels have ever been successful. The clone invariably finds its way to the coffin, though it usually appears to be wandering aimlessly. <ul>\n" +
                "<li>Within 15 minutes, the occupant falls asleep.\n" +
                "<ul>\n" +
                "<li>The occupant will fall asleep under any circumstances; such as, but not limited to, being in the middle of a claustrophobic fit, or being given amounts of caffeine and amphetamines close to causing an overdose. <ul>\n" +
                "<li>Within 15 minutes, the occupant falls asleep.\n" +
                "<ul>\n" +
                "<li>The occupant will fall asleep under any circumstances; such as, but not limited to, being in the middle of a claustrophobic fit, or being given amounts of caffeine and amphetamines close to causing an overdose.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>The occupant sleeps for 1-2 hours.\n" +
                "<ul>\n" +
                "<li>Opening the coffin during this time usually results in the death of the occupant and no further results.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>The occupant wakes up, feeling well rested.\n" +
                "<ul>\n" +
                "<li>The occupant will not fall asleep until the coffin is opened again. The possibility of sleep deprivation research has been advanced but not yet implemented.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>Within 24 hours, a person will be found within the tunnels who is a duplicate, or clone, of the occupant.\n" +
                "<ul>\n" +
                "<li>No attempts to find the origin of the clone within the tunnels have ever been successful. The clone invariably finds its way to the coffin, though it usually appears to be wandering aimlessly.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>The occupant sleeps for 1-2 hours.\n" +
                "<ul>\n" +
                "<li>Opening the coffin during this time usually results in the death of the occupant and no further results.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>The occupant wakes up, feeling well rested.\n" +
                "<ul>\n" +
                "<li>The occupant will not fall asleep until the coffin is opened again. The possibility of sleep deprivation research has been advanced but not yet implemented.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>Within 24 hours, a person will be found within the tunnels who is a duplicate, or clone, of the occupant.\n" +
                "<ul>\n" +
                "<li>No attempts to find the origin of the clone within the tunnels have ever been successful. The clone invariably finds its way to the coffin, though it usually appears to be wandering aimlessly.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>","", Parser.xmlParser())
                .selectFirst("ul");
        String expectedContent = "• Within 15 minutes, the occupant falls asleep. \n" +
                "\t• The occupant will fall asleep under any circumstances; such as, but not limited to, being in the middle of a claustrophobic fit, or being given amounts of caffeine and amphetamines close to causing an overdose.\n" +
                "• The occupant sleeps for 1-2 hours. \n" +
                "\t• Opening the coffin during this time usually results in the death of the occupant and no further results.\n" +
                "• The occupant wakes up, feeling well rested. \n" +
                "\t• The occupant will not fall asleep until the coffin is opened again. The possibility of sleep deprivation research has been advanced but not yet implemented.\n" +
                "• Within 24 hours, a person will be found within the tunnels who is a duplicate, or clone, of the occupant. \n" +
                "\t• No attempts to find the origin of the clone within the tunnels have ever been successful. The clone invariably finds its way to the coffin, though it usually appears to be wandering aimlessly. \n" +
                "\t\t• Within 15 minutes, the occupant falls asleep. \n" +
                "\t\t\t• The occupant will fall asleep under any circumstances; such as, but not limited to, being in the middle of a claustrophobic fit, or being given amounts of caffeine and amphetamines close to causing an overdose. \n" +
                "\t\t\t\t• Within 15 minutes, the occupant falls asleep. \n" +
                "\t\t\t\t\t• The occupant will fall asleep under any circumstances; such as, but not limited to, being in the middle of a claustrophobic fit, or being given amounts of caffeine and amphetamines close to causing an overdose.\n" +
                "\t\t\t\t• The occupant sleeps for 1-2 hours. \n" +
                "\t\t\t\t\t• Opening the coffin during this time usually results in the death of the occupant and no further results.\n" +
                "\t\t\t\t• The occupant wakes up, feeling well rested. \n" +
                "\t\t\t\t\t• The occupant will not fall asleep until the coffin is opened again. The possibility of sleep deprivation research has been advanced but not yet implemented.\n" +
                "\t\t\t\t• Within 24 hours, a person will be found within the tunnels who is a duplicate, or clone, of the occupant. \n" +
                "\t\t\t\t\t• No attempts to find the origin of the clone within the tunnels have ever been successful. The clone invariably finds its way to the coffin, though it usually appears to be wandering aimlessly.\n" +
                "\t\t• The occupant sleeps for 1-2 hours. \n" +
                "\t\t\t• Opening the coffin during this time usually results in the death of the occupant and no further results.\n" +
                "\t\t• The occupant wakes up, feeling well rested. \n" +
                "\t\t\t• The occupant will not fall asleep until the coffin is opened again. The possibility of sleep deprivation research has been advanced but not yet implemented.\n" +
                "\t\t• Within 24 hours, a person will be found within the tunnels who is a duplicate, or clone, of the occupant. \n" +
                "\t\t\t• No attempts to find the origin of the clone within the tunnels have ever been successful. The clone invariably finds its way to the coffin, though it usually appears to be wandering aimlessly.";

        //when
        listMapper.mapElement(simpleUnorderedList, appendices);
        //then
        assertAll(() -> assertFalse(appendices.get(0).hasTitle()),
                () -> assertEquals(expectedContent, appendices.get(0).getContent()));
    }


    @Test
    void shouldMapSimpleOrderedList() {
        //given
        Element simpleOrderedList = Jsoup.parse("<ol>\n" +
                "<li>23-cm-tall human female in French maid's outfit, holding a deck broom (nonremovable)</li>\n" +
                "<li>19-cm-tall human female in swimsuit. Has feline ears (one broken), tail (broken), and paws.</li>\n" +
                "<li>25-cm-tall human female in leather motorcyclist gear with zipper open past its navel. Left forearm missing.</li>\n" +
                "<li>27-cm-tall human male in samurai armor. Face is featureless; communicates by writing Japanese characters with its sword (nonremovable).</li>\n" +
                "<li>20-cm-tall human infant, nude. No genitalia. Has not displayed ability to speak, but is fluent in Japanese Sign Language.</li>\n" +
                "<li>30-cm-tall human male with flensed skin and visible organs. Head is on backwards as a result of attempts at repair.</li>\n" +
                "<li>22-cm-tall human adolescent female in swimsuit. Has disproportionately-large \"anime\"-style eyes (nonfunctional).</li>\n" +
                "<li>24-cm-tall human female in office clothes. Permanently in a seated position.</li>\n" +
                "<li>15-cm-tall emperor penguin (<em>Aptenodytes forsteri</em>).</li>\n" +
                "<li>22-cm-tall human female in \"gothic lolita\" outfit. Back of head is missing.</li>\n" +
                "<li>21-cm-tall human female in gymnast outfit. Feet are unfinished.</li>\n" +
                "<li>23-cm-tall human female in samurai armor.</li>\n" +
                "<li>12-cm-tall anthropomorphic insectoid with top hat, compound eyes, and four articulated hands. Produces speech sounds by rubbing its limbs together.</li>\n" +
                "<li>10-cm-tall anthropomorphic rodent cartoon character. Has disfigured itself.</li>\n" +
                "<li>24-cm-tall skeletal creature. <span style=\"text-decoration: line-through;\">Does not match the skeletal structure of any known species.</span> Identified as the 'composite skeleton' creature depicted in the closing sequence of Walt Disney's <em>Skeleton Dance</em> (1929).</li>\n" +
                "<li>30-cm-tall \"demonic\" humanoid. Right arm held on with duct tape, left arm missing.</li>\n" +
                "</ol>","", Parser.xmlParser())
                .selectFirst("ol");
        String expectedContent =
                "1. 23-cm-tall human female in French maid's outfit, holding a deck broom (nonremovable)\n" +
                "2. 19-cm-tall human female in swimsuit. Has feline ears (one broken), tail (broken), and paws.\n" +
                "3. 25-cm-tall human female in leather motorcyclist gear with zipper open past its navel. Left forearm missing.\n" +
                "4. 27-cm-tall human male in samurai armor. Face is featureless; communicates by writing Japanese characters with its sword (nonremovable).\n" +
                "5. 20-cm-tall human infant, nude. No genitalia. Has not displayed ability to speak, but is fluent in Japanese Sign Language.\n" +
                "6. 30-cm-tall human male with flensed skin and visible organs. Head is on backwards as a result of attempts at repair.\n" +
                "7. 22-cm-tall human adolescent female in swimsuit. Has disproportionately-large \"anime\"-style eyes (nonfunctional).\n" +
                "8. 24-cm-tall human female in office clothes. Permanently in a seated position.\n" +
                "9. 15-cm-tall emperor penguin (Aptenodytes forsteri).\n" +
                "10. 22-cm-tall human female in \"gothic lolita\" outfit. Back of head is missing.\n" +
                "11. 21-cm-tall human female in gymnast outfit. Feet are unfinished.\n" +
                "12. 23-cm-tall human female in samurai armor.\n" +
                "13. 12-cm-tall anthropomorphic insectoid with top hat, compound eyes, and four articulated hands. Produces speech sounds by rubbing its limbs together.\n" +
                "14. 10-cm-tall anthropomorphic rodent cartoon character. Has disfigured itself.\n" +
                "15. 24-cm-tall skeletal creature. "+DeletedContentMarker.DELETED_TEXT_MARK+"Does not match the skeletal structure of any known species."+DeletedContentMarker.DELETED_TEXT_MARK+" Identified as the 'composite skeleton' creature depicted in the closing sequence of Walt Disney's Skeleton Dance (1929).\n" +
                "16. 30-cm-tall \"demonic\" humanoid. Right arm held on with duct tape, left arm missing.";

        //when
        listMapper.mapElement(simpleOrderedList, appendices);
        //then
        assertAll(() -> assertFalse(appendices.get(0).hasTitle()),
                () -> assertEquals(expectedContent, appendices.get(0).getContent()));
    }

    @Test
    void shouldMapOrderedListWithNestedOrderedList() {
        //given
        Element simpleOrderedList = Jsoup.parse("<ol>\n" +
                "<li>23-cm-tall human female in French maid's outfit, holding a deck broom (nonremovable)</li>\n" +
                "<li>19-cm-tall human female in swimsuit. Has feline ears (one broken), tail (broken), and paws.</li>\n" +
                "<li>25-cm-tall human female in leather motorcyclist gear with zipper open past its navel. Left forearm missing.</li>\n" +
                "<li>27-cm-tall human male in samurai armor. Face is featureless; communicates by writing Japanese characters with its sword (nonremovable).</li>\n" +
                "<li>20-cm-tall human infant, nude. No genitalia. Has not displayed ability to speak, but is fluent in Japanese Sign Language.</li>\n" +
                "<li>30-cm-tall human male with flensed skin and visible organs. Head is on backwards as a result of attempts at repair.</li>\n" +
                "<li>22-cm-tall human adolescent female in swimsuit. Has disproportionately-large \"anime\"-style eyes (nonfunctional).</li>\n" +
                "<li>24-cm-tall human female in office clothes. Permanently in a seated position.</li>\n" +
                "<li>15-cm-tall emperor penguin (<em>Aptenodytes forsteri</em>).</li>\n" +
                "<li>22-cm-tall human female in \"gothic lolita\" outfit. Back of head is missing.<ol>\n" +
                "<li>23-cm-tall human female in French maid's outfit, holding a deck broom (nonremovable)</li>\n" +
                "<li>19-cm-tall human female in swimsuit. Has feline ears (one broken), tail (broken), and paws.</li>\n" +
                "<li>25-cm-tall human female in leather motorcyclist gear with zipper open past its navel. Left forearm missing.</li>\n" +
                "<li>27-cm-tall human male in samurai armor. Face is featureless; communicates by writing Japanese characters with its sword (nonremovable).</li>\n" +
                "<li>20-cm-tall human infant, nude. No genitalia. Has not displayed ability to speak, but is fluent in Japanese Sign Language.</li>\n" +
                "<li>30-cm-tall human male with flensed skin and visible organs. Head is on backwards as a result of attempts at repair.</li>\n" +
                "<li>22-cm-tall human adolescent female in swimsuit. Has disproportionately-large \"anime\"-style eyes (nonfunctional).</li>\n" +
                "<li>24-cm-tall human female in office clothes. Permanently in a seated position.</li>\n" +
                "<li>15-cm-tall emperor penguin (<em>Aptenodytes forsteri</em>).</li>\n" +
                "<li>22-cm-tall human female in \"gothic lolita\" outfit. Back of head is missing.</li>\n" +
                "<li>21-cm-tall human female in gymnast outfit. Feet are unfinished.</li>\n" +
                "<li>23-cm-tall human female in samurai armor.</li>\n" +
                "<li>12-cm-tall anthropomorphic insectoid with top hat, compound eyes, and four articulated hands. Produces speech sounds by rubbing its limbs together.</li>\n" +
                "<li>10-cm-tall anthropomorphic rodent cartoon character. Has disfigured itself.</li>\n" +
                "<li>24-cm-tall skeletal creature. <span style=\"text-decoration: line-through;\">Does not match the skeletal structure of any known species.</span> Identified as the 'composite skeleton' creature depicted in the closing sequence of Walt Disney's <em>Skeleton Dance</em> (1929).</li>\n" +
                "<li>30-cm-tall \"demonic\" humanoid. Right arm held on with duct tape, left arm missing.</li>\n" +
                "</ol></li>\n" +
                "<li>21-cm-tall human female in gymnast outfit. Feet are unfinished.</li>\n" +
                "<li>23-cm-tall human female in samurai armor.</li>\n" +
                "<li>12-cm-tall anthropomorphic insectoid with top hat, compound eyes, and four articulated hands. Produces speech sounds by rubbing its limbs together.</li>\n" +
                "<li>10-cm-tall anthropomorphic rodent cartoon character. Has disfigured itself.</li>\n" +
                "<li>24-cm-tall skeletal creature. <span style=\"text-decoration: line-through;\">Does not match the skeletal structure of any known species.</span> Identified as the 'composite skeleton' creature depicted in the closing sequence of Walt Disney's <em>Skeleton Dance</em> (1929).</li>\n" +
                "<li>30-cm-tall \"demonic\" humanoid. Right arm held on with duct tape, left arm missing.</li>\n" +
                "</ol>","", Parser.xmlParser())
                .selectFirst("ol");

        String expectedContent = "1. 23-cm-tall human female in French maid's outfit, holding a deck broom (nonremovable)\n" +
                "2. 19-cm-tall human female in swimsuit. Has feline ears (one broken), tail (broken), and paws.\n" +
                "3. 25-cm-tall human female in leather motorcyclist gear with zipper open past its navel. Left forearm missing.\n" +
                "4. 27-cm-tall human male in samurai armor. Face is featureless; communicates by writing Japanese characters with its sword (nonremovable).\n" +
                "5. 20-cm-tall human infant, nude. No genitalia. Has not displayed ability to speak, but is fluent in Japanese Sign Language.\n" +
                "6. 30-cm-tall human male with flensed skin and visible organs. Head is on backwards as a result of attempts at repair.\n" +
                "7. 22-cm-tall human adolescent female in swimsuit. Has disproportionately-large \"anime\"-style eyes (nonfunctional).\n" +
                "8. 24-cm-tall human female in office clothes. Permanently in a seated position.\n" +
                "9. 15-cm-tall emperor penguin (Aptenodytes forsteri).\n" +
                "10. 22-cm-tall human female in \"gothic lolita\" outfit. Back of head is missing.\n" +
                "\t1. 23-cm-tall human female in French maid's outfit, holding a deck broom (nonremovable)\n" +
                "\t2. 19-cm-tall human female in swimsuit. Has feline ears (one broken), tail (broken), and paws.\n" +
                "\t3. 25-cm-tall human female in leather motorcyclist gear with zipper open past its navel. Left forearm missing.\n" +
                "\t4. 27-cm-tall human male in samurai armor. Face is featureless; communicates by writing Japanese characters with its sword (nonremovable).\n" +
                "\t5. 20-cm-tall human infant, nude. No genitalia. Has not displayed ability to speak, but is fluent in Japanese Sign Language.\n" +
                "\t6. 30-cm-tall human male with flensed skin and visible organs. Head is on backwards as a result of attempts at repair.\n" +
                "\t7. 22-cm-tall human adolescent female in swimsuit. Has disproportionately-large \"anime\"-style eyes (nonfunctional).\n" +
                "\t8. 24-cm-tall human female in office clothes. Permanently in a seated position.\n" +
                "\t9. 15-cm-tall emperor penguin (Aptenodytes forsteri).\n" +
                "\t10. 22-cm-tall human female in \"gothic lolita\" outfit. Back of head is missing.\n" +
                "\t11. 21-cm-tall human female in gymnast outfit. Feet are unfinished.\n" +
                "\t12. 23-cm-tall human female in samurai armor.\n" +
                "\t13. 12-cm-tall anthropomorphic insectoid with top hat, compound eyes, and four articulated hands. Produces speech sounds by rubbing its limbs together.\n" +
                "\t14. 10-cm-tall anthropomorphic rodent cartoon character. Has disfigured itself.\n" +
                "\t15. 24-cm-tall skeletal creature. "+DeletedContentMarker.DELETED_TEXT_MARK+"Does not match the skeletal structure of any known species."+DeletedContentMarker.DELETED_TEXT_MARK+" Identified as the 'composite skeleton' creature depicted in the closing sequence of Walt Disney's Skeleton Dance (1929).\n" +
                "\t16. 30-cm-tall \"demonic\" humanoid. Right arm held on with duct tape, left arm missing.\n" +
                "11. 21-cm-tall human female in gymnast outfit. Feet are unfinished.\n" +
                "12. 23-cm-tall human female in samurai armor.\n" +
                "13. 12-cm-tall anthropomorphic insectoid with top hat, compound eyes, and four articulated hands. Produces speech sounds by rubbing its limbs together.\n" +
                "14. 10-cm-tall anthropomorphic rodent cartoon character. Has disfigured itself.\n" +
                "15. 24-cm-tall skeletal creature. "+DeletedContentMarker.DELETED_TEXT_MARK+"Does not match the skeletal structure of any known species."+DeletedContentMarker.DELETED_TEXT_MARK+" Identified as the 'composite skeleton' creature depicted in the closing sequence of Walt Disney's Skeleton Dance (1929).\n" +
                "16. 30-cm-tall \"demonic\" humanoid. Right arm held on with duct tape, left arm missing.";

        //when
        listMapper.mapElement(simpleOrderedList, appendices);
        //then
        assertAll(() -> assertFalse(appendices.get(0).hasTitle()),
                () -> assertEquals(expectedContent, appendices.get(0).getContent()));

    }

    @Test
    void shouldMapOrderedListWithNestedUnorderedList() {
        //given
        Element simpleOrderedList = Jsoup.parse("<ol>\n" +
                "<li>23-cm-tall human female in French maid's outfit, holding a deck broom (nonremovable)</li>\n" +
                "<li>19-cm-tall human female in swimsuit. Has feline ears (one broken), tail (broken), and paws.</li>\n" +
                "<li>25-cm-tall human female in leather motorcyclist gear with zipper open past its navel. Left forearm missing.</li>\n" +
                "<li>27-cm-tall human male in samurai armor. Face is featureless; communicates by writing Japanese characters with its sword (nonremovable).</li>\n" +
                "<li>20-cm-tall human infant, nude. No genitalia. Has not displayed ability to speak, but is fluent in Japanese Sign Language.</li>\n" +
                "<li>30-cm-tall human male with flensed skin and visible organs. Head is on backwards as a result of attempts at repair.<ul> \n" +
                " <li>Enhanced survivability in the bacterium's natural environment and similar environments;</li> \n" +
                " <li>Full spectrum antibiotic resistance;</li> \n" +
                " <li>Increased reproduction rate and consumption of available material;</li> \n" +
                " <li>Development of a sporulation ability in gram-positive bacteria;</li> \n" +
                " <li>Increased ability to uptake, hold, and share plasmids, particularly in gram-negative bacteria;</li> \n" +
                " <li>Increased transmission, due to traits described above.</li> \n" +
                "</ul></li>\n" +
                "<li>22-cm-tall human adolescent female in swimsuit. Has disproportionately-large \"anime\"-style eyes (nonfunctional).</li>\n" +
                "<li>24-cm-tall human female in office clothes. Permanently in a seated position.</li>\n" +
                "<li>15-cm-tall emperor penguin (<em>Aptenodytes forsteri</em>).</li>\n" +
                "<li>22-cm-tall human female in \"gothic lolita\" outfit. Back of head is missing.</li>\n" +
                "<li>21-cm-tall human female in gymnast outfit. Feet are unfinished.</li>\n" +
                "<li>23-cm-tall human female in samurai armor.</li>\n" +
                "<li>12-cm-tall anthropomorphic insectoid with top hat, compound eyes, and four articulated hands. Produces speech sounds by rubbing its limbs together.</li>\n" +
                "<li>10-cm-tall anthropomorphic rodent cartoon character. Has disfigured itself.</li>\n" +
                "<li>24-cm-tall skeletal creature. <span style=\"text-decoration: line-through;\">Does not match the skeletal structure of any known species.</span> Identified as the 'composite skeleton' creature depicted in the closing sequence of Walt Disney's <em>Skeleton Dance</em> (1929).</li>\n" +
                "<li>30-cm-tall \"demonic\" humanoid. Right arm held on with duct tape, left arm missing.</li>\n" +
                "</ol>","", Parser.xmlParser())
                .selectFirst("ol");

        String expectedContent = "1. 23-cm-tall human female in French maid's outfit, holding a deck broom (nonremovable)\n" +
                "2. 19-cm-tall human female in swimsuit. Has feline ears (one broken), tail (broken), and paws.\n" +
                "3. 25-cm-tall human female in leather motorcyclist gear with zipper open past its navel. Left forearm missing.\n" +
                "4. 27-cm-tall human male in samurai armor. Face is featureless; communicates by writing Japanese characters with its sword (nonremovable).\n" +
                "5. 20-cm-tall human infant, nude. No genitalia. Has not displayed ability to speak, but is fluent in Japanese Sign Language.\n" +
                "6. 30-cm-tall human male with flensed skin and visible organs. Head is on backwards as a result of attempts at repair.\n" +
                "\t• Enhanced survivability in the bacterium's natural environment and similar environments;\n" +
                "\t• Full spectrum antibiotic resistance;\n" +
                "\t• Increased reproduction rate and consumption of available material;\n" +
                "\t• Development of a sporulation ability in gram-positive bacteria;\n" +
                "\t• Increased ability to uptake, hold, and share plasmids, particularly in gram-negative bacteria;\n" +
                "\t• Increased transmission, due to traits described above.\n" +
                "7. 22-cm-tall human adolescent female in swimsuit. Has disproportionately-large \"anime\"-style eyes (nonfunctional).\n" +
                "8. 24-cm-tall human female in office clothes. Permanently in a seated position.\n" +
                "9. 15-cm-tall emperor penguin (Aptenodytes forsteri).\n" +
                "10. 22-cm-tall human female in \"gothic lolita\" outfit. Back of head is missing.\n" +
                "11. 21-cm-tall human female in gymnast outfit. Feet are unfinished.\n" +
                "12. 23-cm-tall human female in samurai armor.\n" +
                "13. 12-cm-tall anthropomorphic insectoid with top hat, compound eyes, and four articulated hands. Produces speech sounds by rubbing its limbs together.\n" +
                "14. 10-cm-tall anthropomorphic rodent cartoon character. Has disfigured itself.\n" +
                "15. 24-cm-tall skeletal creature. "+DeletedContentMarker.DELETED_TEXT_MARK+"Does not match the skeletal structure of any known species."+DeletedContentMarker.DELETED_TEXT_MARK+" Identified as the 'composite skeleton' creature depicted in the closing sequence of Walt Disney's Skeleton Dance (1929).\n" +
                "16. 30-cm-tall \"demonic\" humanoid. Right arm held on with duct tape, left arm missing.";

        //when
        listMapper.mapElement(simpleOrderedList, appendices);
        //then
        assertAll(() -> assertFalse(appendices.get(0).hasTitle()),
                () -> assertEquals(expectedContent, appendices.get(0).getContent()));

    }

    @Test
    void shouldMapListUnorderedListWithNestedLists() {
        //given
        Element unorderedList = Jsoup.parse("<ul>\n" +
                "<li>Within 15 minutes, the occupant falls asleep.\n" +
                "<ul>\n" +
                "<li>The occupant will fall asleep under any circumstances; such as, but not limited to, being in the middle of a claustrophobic fit, or being given amounts of caffeine and amphetamines close to causing an overdose.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>The occupant sleeps for 1-2 hours.\n" +
                "<ul>\n" +
                "<li>Opening the coffin during this time usually results in the death of the occupant and no further results.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li>The occupant wakes up, feeling well rested.\n <ul>\n" +
                "<li>The symbol of gold in the subject's forehead, right underneath the hair line.</li>\n" +
                "<li>The symbol of mercury under the nose, circling both lips.</li>\n" +
                "<li>The symbol of copper between the medial ends of its clavicles.</li>\n" +
                "<li>[DATA EXPUNGED - AUTOCENSOR LEVEL SC 4 - NON-TRIVIAL COGNITOHAZARD DETECTED] with the anatomically correct shape of a human heart placed over the location where a heart would be in a female human of the same apparent age and bodily proportions.</li>\n" +
                "<li>The symbol of iron in the upper abdominal region.</li>\n" +
                "<li>The symbol of tin in the lower abdominal region.</li>\n" +
                "<li>Part of a final symbol in the pelvic region. While the anatomy of this region makes its clear observation difficult, it has been hypothesized that the symbol of lead is also present and complete in the perineum region.</li>\n" +
                "</ul>" +
                "<ul>" +
                "<li>Dupsko:" +
                "<ul>\n" +
                "<li>Enhanced survivability in the bacterium's natural environment and similar environments;</li>\n" +
                "<li>Full spectrum antibiotic resistance;</li>\n" +
                "<li>Increased reproduction rate and consumption of available material;</li>\n" +
                "<li>Development of a sporulation ability in gram-positive bacteria;</li>\n" +
                "<li>Increased ability to uptake, hold, and share plasmids, particularly in gram-negative bacteria;</li>\n" +
                "<li>Increased transmission, due to traits described above.</li>\n" +
                "</ul>" +
                "</li>"+
                "<li>The occupant will not fall asleep until the coffin is opened again. The possibility of sleep deprivation research has been advanced but not yet implemented.</li>\n" +
                "<ol>\n" +
                "<li>Enhanced survivability in the bacterium's natural environment and similar environments;</li>\n" +
                "<li>Full spectrum antibiotic resistance;</li>\n" +
                "<li>Increased reproduction rate and consumption of available material;</li>\n" +
                "<li>Development of a sporulation ability in gram-positive bacteria;</li>\n" +
                "<li>Increased ability to uptake, hold, and share plasmids, particularly in gram-negative bacteria;</li>\n" +
                "<li>Increased transmission, due to traits described above.</li>\n" +
                "</ol>" +
                "</ul>\n" +
                "</li>\n" +
                "<ul>\n" +
                "<li>No attempts to find the origin of the clone within the tunnels have ever been successful. The clone invariably finds its way to the coffin, though it usually appears to be wandering aimlessly.</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>","", Parser.xmlParser())
                .selectFirst("ul");
        String expectedContent = "• Within 15 minutes, the occupant falls asleep. \n" +
                "\t• The occupant will fall asleep under any circumstances; such as, but not limited to, being in the middle of a claustrophobic fit, or being given amounts of caffeine and amphetamines close to causing an overdose.\n" +
                "• The occupant sleeps for 1-2 hours. \n" +
                "\t• Opening the coffin during this time usually results in the death of the occupant and no further results.\n" +
                "• The occupant wakes up, feeling well rested. \n" +
                "\t• The symbol of gold in the subject's forehead, right underneath the hair line.\n" +
                "\t• The symbol of mercury under the nose, circling both lips.\n" +
                "\t• The symbol of copper between the medial ends of its clavicles.\n" +
                "\t• [DATA EXPUNGED - AUTOCENSOR LEVEL SC 4 - NON-TRIVIAL COGNITOHAZARD DETECTED] with the anatomically correct shape of a human heart placed over the location where a heart would be in a female human of the same apparent age and bodily proportions.\n" +
                "\t• The symbol of iron in the upper abdominal region.\n" +
                "\t• The symbol of tin in the lower abdominal region.\n" +
                "\t• Part of a final symbol in the pelvic region. While the anatomy of this region makes its clear observation difficult, it has been hypothesized that the symbol of lead is also present and complete in the perineum region.\n\n" +
                "\t• Dupsko:\n" +
                "\t\t• Enhanced survivability in the bacterium's natural environment and similar environments;\n" +
                "\t\t• Full spectrum antibiotic resistance;\n" +
                "\t\t• Increased reproduction rate and consumption of available material;\n" +
                "\t\t• Development of a sporulation ability in gram-positive bacteria;\n" +
                "\t\t• Increased ability to uptake, hold, and share plasmids, particularly in gram-negative bacteria;\n" +
                "\t\t• Increased transmission, due to traits described above.\n" +
                "\t• The occupant will not fall asleep until the coffin is opened again. The possibility of sleep deprivation research has been advanced but not yet implemented.\n" +
                "\t\t1. Enhanced survivability in the bacterium's natural environment and similar environments;\n" +
                "\t\t2. Full spectrum antibiotic resistance;\n" +
                "\t\t3. Increased reproduction rate and consumption of available material;\n" +
                "\t\t4. Development of a sporulation ability in gram-positive bacteria;\n" +
                "\t\t5. Increased ability to uptake, hold, and share plasmids, particularly in gram-negative bacteria;\n" +
                "\t\t6. Increased transmission, due to traits described above.\n" +
                "\t• No attempts to find the origin of the clone within the tunnels have ever been successful. The clone invariably finds its way to the coffin, though it usually appears to be wandering aimlessly.";

        //when
        listMapper.mapElement(unorderedList, appendices);
        //then
        assertAll(() -> assertFalse(appendices.get(0).hasTitle()),
                () -> assertEquals(expectedContent, appendices.get(0).getContent()));
    }

     */


}