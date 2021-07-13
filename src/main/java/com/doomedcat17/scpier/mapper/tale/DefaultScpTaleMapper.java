package com.doomedcat17.scpier.mapper.tale;

import com.doomedcat17.scpier.data.scp.ScpTale;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import org.jsoup.nodes.Element;

public class DefaultScpTaleMapper implements ScpTaleMapper {
    @Override
    public ScpTale mapToTale(PageContent pageContent) {
        ScpTale scpTale = new ScpTale();
        scpTale.setTitle(pageContent.getName());
        scpTale.setSource(pageContent.getSourceUrl());
        mapTale(scpTale, pageContent.getContent());
        return scpTale;
    }

    private void mapTale(ScpTale scpTale, Element pageContentElement) {
        scpTale.setContent(ElementContentScrapper.scrapContent(pageContentElement, scpTale.getSource()));
    }
}
