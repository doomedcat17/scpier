package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers;

import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.data.content_node.ContentNode;
import pl.doomedcat17.scpapi.data.content_node.ContentNodeType;
import pl.doomedcat17.scpapi.data.content_node.TextNode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TitleFinderTest {

    private TitleFinder titleFinder = new TitleFinder();

    @Test
    void shouldFindTitle() {
        //given
        ContentNode<List<TextNode>> headingNode = new ContentNode<>(ContentNodeType.HEADING, new ArrayList<>(List.of(new TextNode("Description"))));
        List<ContentNode<?>> contentNodes = new ArrayList<>(List.of(
                headingNode,
                new TextNode("Sample Text"),
                new TextNode("Sample Text"),
                new TextNode("Sample Text"),
                new TextNode("Sample Text"),
                new TextNode("Sample Text")
        ));
        //when
        String actualTitle = titleFinder.lookForTittle(contentNodes);
        //then
        String expectedTitle = "Description";
        assertAll(
                () -> assertEquals(expectedTitle, actualTitle),
                () -> assertEquals(5, contentNodes.size())
        );
    }

    @Test
    void shouldNotFindTitle() {
        //given
        ContentNode<List<TextNode>> headingNode = new ContentNode<>(ContentNodeType.HEADING, new ArrayList<>(List.of(new TextNode("Title"))));
        List<ContentNode<?>> contentNodes = new ArrayList<>(List.of(
                headingNode,
                new TextNode("Sample Text"),
                new TextNode("Sample Text"),
                new TextNode("Sample Text"),
                new TextNode("Sample Text"),
                new TextNode("Sample Text")
        ));
        //when
        String actualTitle = titleFinder.lookForTittle(contentNodes);
        //then
        String expectedTitle = "";
        assertAll(
                () -> assertEquals(expectedTitle, actualTitle),
                () -> assertEquals(6, contentNodes.size())
        );
    }

    @Test
    void shouldFindFirstHeadingForTitle() {
        //given
        ContentNode<List<TextNode>> headingNode = new ContentNode<>(ContentNodeType.HEADING, new ArrayList<>(List.of(new TextNode("Description"))));
        List<ContentNode<?>> contentNodes = new ArrayList<>(List.of(
                headingNode,
                new ContentNode<>(ContentNodeType.HEADING, new ArrayList<>(List.of(new TextNode("Sample Text")))),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text")
        ));
        //when
        String actualTitle = titleFinder.lookForTittle(contentNodes);
        //then
        String expectedTitle = "Description";
        assertAll(
                () -> assertEquals(expectedTitle, actualTitle),
                () -> assertEquals(5, contentNodes.size())
        );
    }

}