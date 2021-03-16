package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.doomedcat17.scpier.data.appendix.Appendix;

import java.util.ArrayList;
import java.util.List;

public class ObjectClassBarDivScrapper extends DivScrapper implements DivScrapperComponent {
    public ObjectClassBarDivScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        List<Appendix> appendices = new ArrayList<>();
        mapBarElement(element, appendices);
        return appendices;
    }

    private void mapBarElement(Element barElement, List<Appendix> appendices) {
        List<Element> elements = new ArrayList<>();
        elements.add(barElement.getElementsByClass("sideleft").first());
        elements.add(barElement.getElementsByClass("sidemiddle").first());
        for (Element element: elements) {
            element.children().forEach(child -> appendices.add(mapDivElement(child)));
            }
        }


    private Appendix mapDivElement(Element divElement) {
        Elements spanElements = divElement.getElementsByClass("ocb-text");
        String title = spanElements.get(0).text().trim();
        if (title.charAt(title.length()-1) == ':') title = title.substring(0, title.length()-1);
        String textContent = spanElements.get(1).text().trim();
        Appendix appendix = new Appendix();
        appendix.setTitle(title);
        appendix.addContentNode(new TextNode(textContent));
        return appendix;
    }
}
