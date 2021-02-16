package mapper.htmlscrappers.div.div_scrapper_componetnts;

import org.jsoup.nodes.Element;
import data.appendencies.Appendix;
import data.content_node.ContentNode;

import java.util.ArrayList;
import java.util.List;

public class CollapsibleBlockScrapper extends DivScrapperComponent {
    @Override
    public List<Appendix> scrapDivContent(Element element) {
        Appendix appendix = new Appendix();
        String title = element.selectFirst(".collapsible-block-link").text();
        title = clearCollapsibleBlockTittle(title);
        if (titleFinder.isTittle(title)) appendix.setTitle(title);
        Element collapsibleBlockContent = element.selectFirst(".collapsible-block-content");
        List<ContentNode<?>> contentNodes = scrapElementContent(collapsibleBlockContent);
        appendix.setContents(contentNodes);
        if (!appendix.hasTitle()) {
            appendix.setTitle(titleFinder.lookForTittle(contentNodes));
        }
        return new ArrayList<>(List.of(appendix));
    }

    private String clearCollapsibleBlockTittle(String title){
        if (!title.isBlank()) {
            char firstChar = title.charAt(0);
            if (firstChar == '+' || firstChar == '>' || firstChar == '-') {
                return title.substring(1).trim();
            }
        }
        return title;
    }
}
