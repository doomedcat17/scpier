package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ParagraphNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import com.doomedcat17.scpier.scraper.text.TextScraper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class ACSDivScraper extends DivScraper implements DivScraperComponent {

    private final List<String> ACS_SCP_CLASSES = List.of(".acs-contain", ".acs-secondary", ".acs-disrupt", ".acs-risk");
    public ACSDivScraper(String source) {
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
        List<TextNode> item = TextScraper.scrap(element.selectFirst(".acs-item"), source);
        TextNode itemHeading = item.get(0);
        TextNode itemName = item.get(1);
        if (itemHeading.getContent().charAt(itemHeading.getContent().length()-1) != ' ') itemHeading.setContent(itemHeading.getContent()+" ");
        //adds "SCP-" prefix
        if (!itemName.getContent().toLowerCase().startsWith("scp-")) itemName.setContent("SCP-"+itemName.getContent());
        return new ParagraphNode(item);
    }

    private List<ParagraphNode> scrapScpClasses(Element element)  {
        List<ParagraphNode> paragraphs = new ArrayList<>();
        for (String className: ACS_SCP_CLASSES) {
            Element scpClassElement = element.selectFirst(className);
            if (scpClassElement != null) {
                ParagraphNode scpClassParagraph = scrapScpClass(scpClassElement);
                if (!scpClassParagraph.isEmpty()) paragraphs.add(scpClassParagraph);
            }
        }
        return paragraphs;
    }

    public ParagraphNode scrapScpClass(Element scpClassElement) {
        Element textElement = scpClassElement.selectFirst(".acs-text");
        List<TextNode> textNodes = TextScraper.scrap(textElement, source);
        if (textNodes.size() > 2) {
            //removes trash nodes with only one char
            textNodes.removeIf(textNode -> textNode.getContent().length() == 1);
        }
        TextNode classHeader = textNodes.get(0);
        //create space between classHeader and className
        if (!classHeader.getContent().endsWith(" ")) classHeader.setContent(classHeader.getContent()+' ');
        classHeader.setContent(capitalizeText(classHeader.getContent()));
        TextNode className = textNodes.get(1);
        //check if className isn't template placeholder
        if (!className.getContent().startsWith("{$")) {
            if (className.getContent().startsWith("/")) className.setContent(className.getContent().substring(1));
            className.setContent(capitalizeText(className.getContent()).trim());
            return new ParagraphNode(textNodes);
        } else return new ParagraphNode();
    }
}
