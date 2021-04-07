package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import org.jsoup.nodes.Element;

import java.util.List;

public interface DivScrapperComponent {

    List<ContentNode<?>> scrapDivContent(Element element);
}
