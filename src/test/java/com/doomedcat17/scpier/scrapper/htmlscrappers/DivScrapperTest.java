package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.ImageNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DivScrapperTest extends ScrapperTest {

    private final DivScrapper divMapper = new DivScrapper(SOURCE);

    protected final Element sampleDivs = TestDataProvider
            .getSampleElements("src/test/resources/html/testdata/divs/SampleDivsElements.html");

    @Test
    void shouldMapDivAsText()  {
        //given
        Element simpleDiv = sampleDivs.getElementById("shouldMapDivAsText");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(simpleDiv);
        //then
        assertEquals(ContentNodeType.PARAGRAPH, contentNode.getContentNodeType());
        try {
            List<TextNode> textNodes = (List<TextNode>) contentNode.getContent();
            assertEquals(1, textNodes.size());
            assertEquals("Sample Text", textNodes.get(0).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void shouldMapDivWithOnlyText()  {
        //given
        Element simpleDiv = sampleDivs.getElementById("shouldMapDivWithOnlyText");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(simpleDiv);
        //then
        try {
            List<ContentNode<List<TextNode>>> paragraphs = (List<ContentNode<List<TextNode>>>) contentNode.getContent();
            assertEquals(ContentNodeType.DIV, contentNode.getContentNodeType());
            assertEquals(4, paragraphs.size());
            assertEquals("Sample Text", paragraphs.get(0).getContent().get(0).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void shouldMapDivWithHeading()  {
        //given
        Element element = sampleDivs.getElementById("shouldMapDivWithHeading");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(element);
        //then
        try {
            List<ContentNode<List<ContentNode<?>>>> content = (List<ContentNode<List<ContentNode<?>>>>) contentNode.getContent();
            assertEquals(ContentNodeType.DIV, contentNode.getContentNodeType());
            assertEquals(1, content.get(0).getContent().size());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void shouldMapDivWithMultipleHeadings()  {
        //given
        Element element = sampleDivs.getElementById("shouldMapDivWithMultipleHeadings");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(element);
        //then
        try {
            List<ContentNode<List<ContentNode<?>>>> content = (List<ContentNode<List<ContentNode<?>>>>) contentNode.getContent();
            assertEquals(ContentNodeType.DIV, contentNode.getContentNodeType());
            assertEquals(7, content.size());
            assertEquals("Description", content.get(0).getContent().get(0).getContent());
            assertEquals(ContentNodeType.HEADING, content.get(0).getContentNodeType());
            assertEquals("Experiment", content.get(1).getContent().get(0).getContent());
            assertEquals(ContentNodeType.HEADING, content.get(1).getContentNodeType());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void shouldMapOnlyImage()  {
        //given
        Element element = sampleDivs.getElementById("shouldMapOnlyImage");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(element);
        //then
        try {
            assertEquals(ContentNodeType.IMAGE, contentNode.getContentNodeType());

        } catch (Exception e) {
            fail();
        }
    }

    /*

     */
    @Test
    void shouldScrapOnlyImages()  {
        //given
        Element element = sampleDivs.getElementById("shouldScrapOnlyImages");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(element);
        //then
        assertEquals(ContentNodeType.DIV, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<?>>> divNode = (ContentNode<List<ContentNode<?>>>) contentNode;

            assertEquals(3, divNode.getContent().size());
            assertTrue(divNode.getContent().stream().allMatch(node -> node.getContentNodeType().equals(ContentNodeType.IMAGE)));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void shouldScrapDivInsideDiv()  {
        //given
        Element element = sampleDivs.getElementById("shouldScrapDivInsideDiv");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(element);
        //then
        assertEquals(ContentNodeType.DIV, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<?>>> divNode = (ContentNode<List<ContentNode<?>>>) contentNode;

            assertEquals(5, divNode.getContent().size());
            assertEquals(ContentNodeType.HEADING, divNode.getContent().get(0).getContentNodeType());
            assertEquals(ContentNodeType.HEADING, divNode.getContent().get(1).getContentNodeType());
            assertEquals(ContentNodeType.PARAGRAPH, divNode.getContent().get(2).getContentNodeType());
            assertEquals(ContentNodeType.PARAGRAPH, divNode.getContent().get(3).getContentNodeType());
            assertEquals(ContentNodeType.DIV, divNode.getContent().get(4).getContentNodeType());
            ContentNode<List<ContentNode<?>>> innerDivNode = (ContentNode<List<ContentNode<?>>>) divNode.getContent().get(4);
            assertEquals(7, innerDivNode.getContent().size());
            assertEquals(ContentNodeType.HEADING, innerDivNode.getContent().get(0).getContentNodeType());
            assertEquals(ContentNodeType.HEADING, innerDivNode.getContent().get(1).getContentNodeType());
            assertEquals(ContentNodeType.HEADING, innerDivNode.getContent().get(2).getContentNodeType());
            assertEquals(ContentNodeType.PARAGRAPH, innerDivNode.getContent().get(3).getContentNodeType());
            assertEquals(ContentNodeType.PARAGRAPH, innerDivNode.getContent().get(4).getContentNodeType());
            assertEquals(ContentNodeType.PARAGRAPH, innerDivNode.getContent().get(5).getContentNodeType());
            assertEquals(ContentNodeType.PARAGRAPH, innerDivNode.getContent().get(6).getContentNodeType());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void shouldScrapEnbaseDiv()  {
        //given
        Element enBaseElement = sampleDivs.getElementById("shouldScrapEnbaseDiv");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(enBaseElement);
        //then
        assertEquals(ContentNodeType.CONTENT_NODES, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<List<TextNode>>>> node = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;

            assertEquals(2, node.getContent().size());

            assertEquals("Item #: ", node.getContent().get(0).getContent().get(0).getContent());
            assertTrue(node.getContent().get(0).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("SCP-1780", node.getContent().get(0).getContent().get(1).getContent());

            assertEquals("Object Class: ", node.getContent().get(1).getContent().get(0).getContent());
            assertTrue(node.getContent().get(1).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Safe", node.getContent().get(1).getContent().get(1).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    void shouldScrapEnbaseDiv2()  {
        //given
        Element enBaseElement = sampleDivs.getElementById("shouldScrapEnbaseDiv2");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(enBaseElement);
        //then
        assertEquals(ContentNodeType.CONTENT_NODES, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<List<TextNode>>>> node = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;

            assertEquals(2, node.getContent().size());

            assertEquals("Item #: ", node.getContent().get(0).getContent().get(0).getContent());
            assertTrue(node.getContent().get(0).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("SCP-2410", node.getContent().get(0).getContent().get(1).getContent());

            assertEquals("Object Class: ", node.getContent().get(1).getContent().get(0).getContent());
            assertTrue(node.getContent().get(1).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Keter", node.getContent().get(1).getContent().get(1).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    void shouldMapACSDiv()  {
        //given
        Element ACSElement = sampleDivs.getElementById("shouldMapACSDiv");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(ACSElement);
        //then
        assertEquals(ContentNodeType.CONTENT_NODES, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<List<TextNode>>>> node = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;

            assertEquals(4, node.getContent().size());

            assertEquals("Item#: ", node.getContent().get(0).getContent().get(0).getContent());
            assertTrue(node.getContent().get(0).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("SCP-306", node.getContent().get(0).getContent().get(1).getContent());

            assertEquals("Containment Class: ", node.getContent().get(1).getContent().get(0).getContent());
            assertTrue(node.getContent().get(1).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Keter", node.getContent().get(1).getContent().get(1).getContent());

            assertEquals("Disruption Class: ", node.getContent().get(2).getContent().get(0).getContent());
            assertTrue(node.getContent().get(2).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Ekhi", node.getContent().get(2).getContent().get(1).getContent());

            assertEquals("Risk Class: ", node.getContent().get(3).getContent().get(0).getContent());
            assertTrue(node.getContent().get(3).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Danger", node.getContent().get(3).getContent().get(1).getContent());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    void shouldMapACSDiv2()  {
        //given
        Element ACSElement = sampleDivs.getElementById("shouldMapACSDiv2");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(ACSElement);
        //then
        assertEquals(ContentNodeType.CONTENT_NODES, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<List<TextNode>>>> node = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;

            assertEquals(5, node.getContent().size());

            assertEquals("Item#: ", node.getContent().get(0).getContent().get(0).getContent());
            assertTrue(node.getContent().get(0).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("SCP-2105", node.getContent().get(0).getContent().get(1).getContent());

            assertEquals("Containment Class: ", node.getContent().get(1).getContent().get(0).getContent());
            assertTrue(node.getContent().get(1).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Euclid", node.getContent().get(1).getContent().get(1).getContent());

            assertEquals("Secondary Class: ", node.getContent().get(2).getContent().get(0).getContent());
            assertTrue(node.getContent().get(2).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Apollyon", node.getContent().get(2).getContent().get(1).getContent());

            assertEquals("Disruption Class: ", node.getContent().get(3).getContent().get(0).getContent());
            assertTrue(node.getContent().get(3).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Vlam", node.getContent().get(3).getContent().get(1).getContent());

            assertEquals("Risk Class: ", node.getContent().get(4).getContent().get(0).getContent());
            assertTrue(node.getContent().get(4).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Notice", node.getContent().get(4).getContent().get(1).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }


    }

    @Test
    void shouldScrapAdultWarning()  {
        //given
        Element element = sampleDivs.getElementById("u-adult-warning");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(element);
        //then
        assertEquals(ContentNodeType.DIV, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<List<TextNode>>>> divNode = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;
            assertEquals(3, divNode.getContent().size());
            assertEquals("ADULT CONTENT", divNode.getContent().get(0).getContent().get(0).getContent());
            assertEquals("The following article contains content of an adult nature.", divNode.getContent().get(1).getContent().get(0).getContent());
            assertEquals("Do not proceed if you are not over the age of 18 or are not willing to see such content.", divNode.getContent().get(2).getContent().get(0).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }


    }

    @Test
    void shouldScrapImageBlockWithAElement()  {
        //given
        Element element = sampleDivs.getElementById("shouldScrapImageBlockWithAElement");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(element);
        //then
        assertTrue(contentNode instanceof ImageNode);
        ImageNode scrappedImageNode = (ImageNode) contentNode;
        assertEquals("Stagnant pond water, Time=0 min.", scrappedImageNode.getCaption().get(0).getContent());
        assertEquals("Stagnant pond water, Time=0 min.", scrappedImageNode.getCaption().get(0).getContent());
        assertEquals("http://scp-wiki.wdfiles.com/local--files/scp-242/242-1.jpg", scrappedImageNode.getContent());

    }

    @Test
    void shouldScrapFootnotes()  {
        //given
        Element element = sampleDivs.getElementById("shouldScrapFootnotes");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(element);
        //then
        assertEquals(ContentNodeType.CONTENT_NODES, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<List<TextNode>>>> node = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;
            assertEquals(ContentNodeType.HEADING, node.getContent().get(0).getContentNodeType());
            assertEquals("Footnotes", node.getContent().get(0).getContent().get(0).getContent());
            node.getContent().remove(0);
            assertTrue(node.getContent().stream().allMatch(paragraph -> paragraph.getContent().size() == 2));


        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }


    }

    @Test
    void shouldScrapAnomBar()  {
        //given
        Element anomBarElement = sampleDivs.getElementById("shouldScrapAnomBar");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(anomBarElement);
        //then
        assertEquals(ContentNodeType.CONTENT_NODES, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<List<TextNode>>>> node = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;

            assertEquals(5, node.getContent().size());

            assertEquals("Item#: ", node.getContent().get(0).getContent().get(0).getContent());
            assertTrue(node.getContent().get(0).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("SCP-5004", node.getContent().get(0).getContent().get(1).getContent());

            assertEquals("Containment Class: ", node.getContent().get(1).getContent().get(0).getContent());
            assertTrue(node.getContent().get(1).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Esoteric", node.getContent().get(1).getContent().get(1).getContent());

            assertEquals("Secondary Class: ", node.getContent().get(2).getContent().get(0).getContent());
            assertTrue(node.getContent().get(2).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Thaumiel", node.getContent().get(2).getContent().get(1).getContent());

            assertEquals("Disruption Class: ", node.getContent().get(3).getContent().get(0).getContent());
            assertTrue(node.getContent().get(3).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Ekhi", node.getContent().get(3).getContent().get(1).getContent());

            assertEquals("Risk Class: ", node.getContent().get(4).getContent().get(0).getContent());
            assertTrue(node.getContent().get(4).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Notice", node.getContent().get(4).getContent().get(1).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }



    @Test
    void shouldScrapAnomBar2()  {
        //given
        Element anomBarElement = sampleDivs.getElementById("shouldScrapAnomBar2");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(anomBarElement);
        //then
        assertEquals(ContentNodeType.CONTENT_NODES, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<List<TextNode>>>> node = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;

            assertEquals(4, node.getContent().size());

            assertEquals("Item#: ", node.getContent().get(0).getContent().get(0).getContent());
            assertTrue(node.getContent().get(0).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("SCP-2105", node.getContent().get(0).getContent().get(1).getContent());

            assertEquals("Containment Class: ", node.getContent().get(1).getContent().get(0).getContent());
            assertTrue(node.getContent().get(1).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Euclid", node.getContent().get(1).getContent().get(1).getContent());

            assertEquals("Disruption Class: ", node.getContent().get(2).getContent().get(0).getContent());
            assertTrue(node.getContent().get(2).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Vlam", node.getContent().get(2).getContent().get(1).getContent());

            assertEquals("Risk Class: ", node.getContent().get(3).getContent().get(0).getContent());
            assertTrue(node.getContent().get(3).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Notice", node.getContent().get(3).getContent().get(1).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }
    @Test
    void shouldScrapAnomBar3()  {
        //given
        Element anomBarElement = sampleDivs.getElementById("shouldScrapAnomBar3");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(anomBarElement);
        //then
        assertEquals(ContentNodeType.CONTENT_NODES, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<List<TextNode>>>> node = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;

            assertEquals(2, node.getContent().size());

            assertEquals("Item#: ", node.getContent().get(0).getContent().get(0).getContent());
            assertTrue(node.getContent().get(0).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("SCP-4511", node.getContent().get(0).getContent().get(1).getContent());

            assertEquals("Containment Class: ", node.getContent().get(1).getContent().get(0).getContent());
            assertTrue(node.getContent().get(1).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Pending", node.getContent().get(1).getContent().get(1).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }



    }

    @Test
    void shouldScrapObjClassBar()  {
        //given
        Element anomBarElement = sampleDivs.getElementById("shouldScrapObjClassBar");
        //when
        ContentNode<?> contentNode = divMapper.scrapElement(anomBarElement);
        //then
        assertEquals(ContentNodeType.CONTENT_NODES, contentNode.getContentNodeType());
        try {
            ContentNode<List<ContentNode<List<TextNode>>>> node = (ContentNode<List<ContentNode<List<TextNode>>>>) contentNode;

            assertEquals(6, node.getContent().size());

            assertEquals("Item #: ", node.getContent().get(0).getContent().get(0).getContent());
            assertTrue(node.getContent().get(0).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("SCP-4444", node.getContent().get(0).getContent().get(1).getContent());

            assertEquals("Object Class: ", node.getContent().get(1).getContent().get(0).getContent());
            assertTrue(node.getContent().get(1).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Keter", node.getContent().get(1).getContent().get(1).getContent());

            assertEquals("Site Responsible: ", node.getContent().get(2).getContent().get(0).getContent());
            assertTrue(node.getContent().get(2).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("USMILA Site-19", node.getContent().get(2).getContent().get(1).getContent());

            assertEquals("Director: ", node.getContent().get(3).getContent().get(0).getContent());
            assertTrue(node.getContent().get(3).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("Tilda Moose", node.getContent().get(3).getContent().get(1).getContent());

            assertEquals("Research Head: ", node.getContent().get(4).getContent().get(0).getContent());
            assertTrue(node.getContent().get(4).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("K. P. Crow", node.getContent().get(4).getContent().get(1).getContent());

            assertEquals("Assigned Task Force: ", node.getContent().get(5).getContent().get(0).getContent());
            assertTrue(node.getContent().get(5).getContent().get(0).getStyles().containsKey("font-weight"));
            assertEquals("N/A", node.getContent().get(5).getContent().get(1).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }



    }



}