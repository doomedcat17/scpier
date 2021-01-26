package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class HtmlMapperTest extends HtmlMapper {
    @Override
    public Appendix mapElement(Element element) {
        return null;
    }


    @Test
    void shouldFindTitle() {
        //given
        List<ContentNode<?>> contentNodes = new ArrayList<>(List.of(
                new ContentNode<>(ContentNodeType.HEADING, "Description"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text")
        ));
        //when
        String actualTitle = lookForTittle(contentNodes);
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
        List<ContentNode<?>> contentNodes = new ArrayList<>(List.of(
                new ContentNode<>(ContentNodeType.HEADING, "Title"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text")
        ));
        //when
        String actualTitle = lookForTittle(contentNodes);
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
        List<ContentNode<?>> contentNodes = new ArrayList<>(List.of(
                new ContentNode<>(ContentNodeType.HEADING, "Description"),
                new ContentNode<>(ContentNodeType.HEADING, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text"),
                new ContentNode<>(ContentNodeType.TEXT, "Sample Text")
        ));
        //when
        String actualTitle = lookForTittle(contentNodes);
        //then
        String expectedTitle = "Description";
        assertAll(
                () -> assertEquals(expectedTitle, actualTitle),
                () -> assertEquals(5, contentNodes.size())
        );
    }
}