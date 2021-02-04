package domain.scp.mapper.htmlscrappers.div.div_scrapper_componetnts;

import org.jsoup.nodes.Element;
import data.appendencies.Appendix;
import data.content_node.ContentNode;

import java.util.ArrayList;
import java.util.List;

public class DefaultDivScrapper extends DivScrapperComponent {

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        List<ContentNode<?>> contentNodes = scrapElementContent(element);
        Appendix appendix = new Appendix();
        appendix.setContents(contentNodes);
        appendix.setTitle(titleFinder.lookForTittle(contentNodes));
        return new ArrayList<>(List.of(appendix));
    }
}
