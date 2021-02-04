package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.div.div_scrapper_componetnts;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.div.DivScrapper;

import java.util.List;

public abstract class DivScrapperComponent extends DivScrapper {

    abstract public List<Appendix> scrapDivContent(Element element);
}
