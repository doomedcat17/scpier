package mapper.htmlscrappers.div.div_scrapper_componetnts;

import org.jsoup.nodes.Element;
import data.appendencies.Appendix;
import mapper.htmlscrappers.div.DivScrapper;

import java.util.List;

public abstract class DivScrapperComponent extends DivScrapper {

    abstract public List<Appendix> scrapDivContent(Element element);
}
