package com.doomedcat17.scpier.mapper.tale;

import com.doomedcat17.scpier.data.scp.ScpTale;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.doomedcat17.scpier.scrapper.htmlscrappers.line.LineScrapper;
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
        LineScrapper lineScrapper = new LineScrapper(scpTale.getSource());
        scpTale.setContent(lineScrapper.scrapContent(pageContentElement));
    }
}
