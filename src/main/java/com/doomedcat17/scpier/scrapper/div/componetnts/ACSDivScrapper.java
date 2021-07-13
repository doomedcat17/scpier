package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.text.TextScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class ACSDivScrapper extends DivScrapper implements DivScrapperComponent {

    private final List<String> ACS_SCP_CLASSES = List.of(".acs-contain", ".acs-secondary", ".acs-disrupt", ".acs-risk");
    public ACSDivScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        contentNodes.add(scrapItemName(element));
        contentNodes.addAll(scrapScpClasses(element));
        return contentNodes;
    }

    private ContentNode<List<TextNode>> scrapItemName(Element element)  {
        List<TextNode> item = TextScrapper.scrapText(element.selectFirst(".acs-item"), source);
        TextNode itemHeading = item.get(0);
        TextNode itemName = item.get(1);
        if (itemHeading.getContent().charAt(itemHeading.getContent().length()-1) != ' ') itemHeading.setContent(itemHeading.getContent()+" ");
        if (!itemName.getContent().toLowerCase().startsWith("scp-")) itemName.setContent("SCP-"+itemName.getContent());
        return new ContentNode<>(ContentNodeType.PARAGRAPH, item);
    }

    private List<ContentNode<List<TextNode>>> scrapScpClasses(Element element)  {
        List<ContentNode<List<TextNode>>> paragraphs = new ArrayList<>();
        for (String className: ACS_SCP_CLASSES) {
            Element scpClassElement = element.selectFirst(className);
            if (scpClassElement != null) {
                Element textElement = scpClassElement.selectFirst(".acs-text");
                List<TextNode> textNodes = TextScrapper.scrapText(textElement, source);
                if (textNodes.size() > 2) {
                    textNodes.removeIf(textNode -> textNode.getContent().length() == 1);
                }
                TextNode classHeader = textNodes.get(0);
                if (!classHeader.getContent().endsWith(" ")) classHeader.setContent(classHeader.getContent()+' ');
                classHeader.setContent(capitalizeText(classHeader.getContent()));
                TextNode classContent = textNodes.get(1);
                if (!classContent.getContent().startsWith("{$")) {
                    if (classContent.getContent().startsWith("/")) classContent.setContent(classContent.getContent().substring(1));
                    classContent.setContent(capitalizeText(classContent.getContent()).trim());
                    ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, textNodes);
                    paragraphs.add(paragraph);
                }
            }
        }
        return paragraphs;
    }
}
