package mapper.htmlscrappers.div.div_scrapper_componetnts;

import org.jsoup.nodes.Element;
import data.appendencies.Appendix;
import data.content_node.ContentNode;
import data.content_node.ContentNodeType;

import java.util.ArrayList;
import java.util.List;

public class ENbaseDivScrapper extends DivScrapperComponent {
    @Override
    public List<Appendix> scrapDivContent(Element element) {
        List<Appendix> appendices = new ArrayList<>();
        String[] scpItem = element.getElementsByClass("itemnum EN")
                .get(0)
                .text()
                .split(":");
        Appendix itemAppendix = new Appendix();
        itemAppendix.setTitle(scpItem[0].trim());
        itemAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, scpItem[1].trim()));
        appendices.add(itemAppendix);
        String scpClassName = element.getElementsByClass("obj-text")
                .get(0)
                .text().trim();
        Appendix classAppendix = new Appendix();
        classAppendix.setTitle("Object Class");
        classAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, capitalizeText(scpClassName)));
        appendices.add(classAppendix);
        return appendices;
    }

}
