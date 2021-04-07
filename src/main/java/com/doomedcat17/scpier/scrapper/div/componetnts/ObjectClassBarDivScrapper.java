package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ObjectClassBarDivScrapper extends DivScrapper implements DivScrapperComponent {
    public ObjectClassBarDivScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) {
        List<ContentNode<?>> barContent = new ArrayList<>();
        List<Element> elements = new ArrayList<>();
        elements.add(element.getElementsByClass("sideleft").first());
        elements.add(element.getElementsByClass("sidemiddle").first());
        for (Element e : elements) {
            e.children().forEach(child -> barContent.add(mapDivElement(child)));
        }
        return barContent;
    }

    private ContentNode<?> mapDivElement(Element divElement) {
        Elements spanElements = divElement.getElementsByClass("ocb-text");
        String title = spanElements.get(0).text().stripLeading();
        String textContent = spanElements.get(1).text().stripTrailing();
        TextNode strongNode = new TextNode(title);
        strongNode.addStyle("font-weight", "bold");
        TextNode content = new TextNode(textContent);
        ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
        paragraph.getContent().add(strongNode);
        paragraph.getContent().add(content);
        return paragraph;
    }
}
