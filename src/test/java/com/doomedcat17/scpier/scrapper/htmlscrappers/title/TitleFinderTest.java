package com.doomedcat17.scpier.scrapper.htmlscrappers.title;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class TitleFinderTest {

    private final TitleResolver titleResolver = TitleResolverProvider.getTitleResolver("eng");

    private final Map<String, List<ContentNode<?>>> contentNodeListsMap =
            TestDataProvider.getSampleContentNodes("src/test/resources/html/test_data/titles/eng/sample_node_lists.json");

    @Test
    void shouldFindDescriptionTitle() {
        //given
        List<ContentNode<?>> contentNodes = contentNodeListsMap.get("sampleNodesWithDescriptionTitle");
        ContentNode<List<TextNode>> paragraphNode = (ContentNode<List<TextNode>>) contentNodes.get(0);
        int paragraphSizeBefore = paragraphNode.getContent().size();
        //when
        String title = TitleFinder.lookForTitle(contentNodes, titleResolver);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("Description", title),
                () -> Assertions.assertEquals(paragraphSizeBefore - 1, paragraphNode.getContent().size())
        );
    }

    @Test
    void shouldFindItemTitle() {
        //given
        List<ContentNode<?>> contentNodes = contentNodeListsMap.get("sampleNodesWithItemTitle");
        ContentNode<List<TextNode>> paragraphNode = (ContentNode<List<TextNode>>) contentNodes.get(0);
        int paragraphSizeBefore = paragraphNode.getContent().size();
        //when
        String title = TitleFinder.lookForTitle(contentNodes, titleResolver);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("Item#", title),
                () -> Assertions.assertEquals(paragraphSizeBefore - 1, paragraphNode.getContent().size())
        );
    }

    @Test
    void shouldFindSpecialContainmentProceduresTitle() {
        //given
        List<ContentNode<?>> contentNodes = contentNodeListsMap.get("sampleNodesWithSpecialContainmentProceduresTitle");
        ContentNode<List<TextNode>> paragraphNode = (ContentNode<List<TextNode>>) contentNodes.get(0);
        int paragraphSizeBefore = paragraphNode.getContent().size();
        //when
        String title = TitleFinder.lookForTitle(contentNodes, titleResolver);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("Special Containment Procedures", title),
                () -> Assertions.assertEquals(paragraphSizeBefore - 1, paragraphNode.getContent().size())
        );
    }

    @Test
    void shouldFindAppendixTitle() {
        //given
        List<ContentNode<?>> contentNodes = contentNodeListsMap.get("sampleNodesWithAppendixTitle");
        ContentNode<List<TextNode>> paragraphNode = (ContentNode<List<TextNode>>) contentNodes.get(0);
        int paragraphSizeBefore = paragraphNode.getContent().size();
        //when
        String title = TitleFinder.lookForTitle(contentNodes, titleResolver);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("Appendix", title),
                () -> Assertions.assertEquals(paragraphSizeBefore - 1, paragraphNode.getContent().size())
        );
    }

    @Test
    void shouldFindHeadingTitle() {
        //given
        List<ContentNode<?>> contentNodes = contentNodeListsMap.get("shouldFindHeadingTitle");
        ContentNode<List<TextNode>> paragraphNode = (ContentNode<List<TextNode>>) contentNodes.get(0);
        int paragraphSizeBefore = paragraphNode.getContent().size();
        //when
        String title = TitleFinder.lookForTitle(contentNodes, titleResolver);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("Appendix", title),
                () -> Assertions.assertEquals(paragraphSizeBefore - 1, paragraphNode.getContent().size())
        );
    }

    @Test
    void shouldFindHeadingTitle2() {
        //given
        List<ContentNode<?>> contentNodes = contentNodeListsMap.get("shouldFindHeadingTitle2");
        int paragraphSizeBefore = contentNodes.size();
        //when
        String title = TitleFinder.lookForTitle(contentNodes, titleResolver);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("Appendix", title),
                () -> Assertions.assertEquals(paragraphSizeBefore - 1, contentNodes.size())
        );
    }

    @Test
    void shouldFindTitleInsideDiv() {
        //given
        List<ContentNode<?>> contentNodes = contentNodeListsMap.get("shouldFindTitleInsideDiv");
        ContentNode<List<ContentNode<?>>> divNode = (ContentNode<List<ContentNode<?>>>) contentNodes.get(0);
        ContentNode<List<TextNode>> innerParagraph = (ContentNode<List<TextNode>>) divNode.getContent().get(0);
        int innerParagraphSizeBefore = innerParagraph.getContent().size();
        //when
        String title = TitleFinder.lookForTitle(contentNodes, titleResolver);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("Appendix", title),
                () -> Assertions.assertEquals(innerParagraphSizeBefore - 1, innerParagraph.getContent().size())
        );
    }

    @Test
    void shouldFindTitleInsideBlockquote() {
        //given
        List<ContentNode<?>> contentNodes = contentNodeListsMap.get("shouldFindTitleInsideBlockquote");
        ContentNode<List<ContentNode<?>>> blockQuote = (ContentNode<List<ContentNode<?>>>) contentNodes.get(0);
        ContentNode<List<TextNode>> innerParagraph = (ContentNode<List<TextNode>>) blockQuote.getContent().get(0);
        int innerParagraphSizeBefore = innerParagraph.getContent().size();
        //when
        String title = TitleFinder.lookForTitle(contentNodes, titleResolver);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("Appendix", title),
                () -> Assertions.assertEquals(innerParagraphSizeBefore - 1, innerParagraph.getContent().size())
        );
    }

    @Test
    void shouldNotFindTitle() {
        //given
        List<ContentNode<?>> contentNodes = contentNodeListsMap.get("shouldNotFindTitle");
        ContentNode<List<TextNode>> paragraphNode = (ContentNode<List<TextNode>>) contentNodes.get(0);
        int paragraphSizeBefore = paragraphNode.getContent().size();
        //when
        String title = TitleFinder.lookForTitle(contentNodes, titleResolver);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals("", title),
                () -> Assertions.assertEquals(paragraphSizeBefore, paragraphNode.getContent().size())
        );
    }

}