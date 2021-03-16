package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;

import java.util.List;

public interface DivScrapperComponent {

    List<Appendix> scrapDivContent(Element element);
}
