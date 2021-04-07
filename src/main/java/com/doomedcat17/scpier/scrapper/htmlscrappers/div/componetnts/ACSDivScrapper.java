package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class ACSDivScrapper extends DivScrapper implements DivScrapperComponent {
    public ACSDivScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        Element scpClassElement = element.getElementsByClass("acs-contain-container").first().getElementsByClass("acs-text").first();
        ContentNode<List<TextNode>> scpClassParagraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
        TextNode objectClassNode = new TextNode("Object Class: ");
        objectClassNode.addStyle("font-weight", "bold");
        scpClassParagraph.getContent().add(objectClassNode);
        scpClassParagraph.getContent().add(new TextNode(capitalizeText(scpClassElement.child(1).text())));
        contentNodes.add(scpClassParagraph);
        mapRestOfClasses(element, contentNodes);
        return contentNodes;
    }

    private void mapRestOfClasses(Element element, List<ContentNode<?>> contentNodes) {
        mapSecondaryClass(element, contentNodes);
        mapDisruptClass(element, contentNodes);
        mapRiskClass(element, contentNodes);
    }

    private void mapSecondaryClass(Element element, List<ContentNode<?>> contentNodes) {
        Element secondaryClassElement = element.getElementsByClass("acs-secondary").first();
        if (secondaryClassElement != null) {
            String secondaryClassText = secondaryClassElement.getElementsByClass("acs-text")
                    .first()
                    .child(1)
                    .text();
            if (!secondaryClassText.equals("{$secondary-class}")) {
                TextNode secondaryClassNode = new TextNode("Secondary Class: ");
                secondaryClassNode.addStyle("font-weight", "bold");
                TextNode secondaryClassNameNode = new TextNode(capitalizeText(secondaryClassText));
                ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
                paragraph.getContent().add(secondaryClassNode);
                paragraph.getContent().add(secondaryClassNameNode);
                contentNodes.add(paragraph);
            }
        }
    }

    private void mapDisruptClass(Element element, List<ContentNode<?>> contentNodes) {
        Element disruptClassElement = element.getElementsByClass("acs-disrupt").first();
        if (disruptClassElement != null) {
            String disruptClassText = disruptClassElement.getElementsByClass("acs-text")
                    .first()
                    .childNode(3)
                    .toString();
            if (!disruptClassText.equals("{$disrupt-class}")) {
                if (disruptClassText.charAt(0) == '/') disruptClassText = disruptClassText.substring(1);
                TextNode disruptionClassNode = new TextNode("Disruption Class: ");
                disruptionClassNode.addStyle("font-weight", "bold");
                TextNode disruptionClassNameNode = new TextNode(capitalizeText(disruptClassText));
                ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
                paragraph.getContent().add(disruptionClassNode);
                paragraph.getContent().add(disruptionClassNameNode);
                contentNodes.add(paragraph);
            }
        }
    }

    private void mapRiskClass(Element element, List<ContentNode<?>> contentNodes) {
        Element riskClassElement = element.getElementsByClass("acs-risk").first();
        if (riskClassElement != null) {
            String riskClassText = riskClassElement.getElementsByClass("acs-text")
                    .first()
                    .childNode(3)
                    .toString();
            if (!riskClassText.equals("{$danger-class}")) {
                if (riskClassText.charAt(0) == '/') riskClassText = riskClassText.substring(1);
                TextNode riskClassNode = new TextNode("Risk Class: ");
                riskClassNode.addStyle("font-weight", "bold");
                TextNode riskClassNameNode = new TextNode(capitalizeText(riskClassText));
                ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
                paragraph.getContent().add(riskClassNode);
                paragraph.getContent().add(riskClassNameNode);
                contentNodes.add(paragraph);
            }
        }
    }
}
