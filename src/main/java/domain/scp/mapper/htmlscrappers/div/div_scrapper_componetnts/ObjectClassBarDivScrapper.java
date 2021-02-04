package domain.scp.mapper.htmlscrappers.div.div_scrapper_componetnts;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import data.appendencies.Appendix;
import data.content_node.ContentNode;
import data.content_node.ContentNodeType;

import java.util.ArrayList;
import java.util.List;

public class ObjectClassBarDivScrapper extends DivScrapperComponent {
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
        appendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, textContent));
        return appendix;
    }
}
