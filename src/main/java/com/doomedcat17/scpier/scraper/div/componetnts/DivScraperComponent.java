package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import org.jsoup.nodes.Element;

import java.util.List;

public interface DivScraperComponent {

    List<ContentNode<?>> scrapDivContent(Element element) ;
}
