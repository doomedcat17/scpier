package com.doomedcat17.scpier.mapper.tale;

import com.doomedcat17.scpier.data.scp.ScpTale;
import com.doomedcat17.scpier.exception.ScpTaleMapperException;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import org.jsoup.nodes.Element;

public class DefaultScpTaleMapper implements ScpTaleMapper {
    @Override
    public ScpTale mapToTale(PageContent pageContent) {
        try {
            ScpTale scpTale = new ScpTale();
            scpTale.setTitle(pageContent.getName());
            scpTale.setSource(pageContent.getSourceUrl());
            mapTale(scpTale, pageContent.getContent());
            return scpTale;
        } catch (Exception e) {
            throw new ScpTaleMapperException(e.getMessage());
        }
    }

    private void mapTale(ScpTale scpTale, Element pageContentElement)  {
        scpTale.setContent(ElementContentScrapper.scrapContent(pageContentElement, scpTale.getSource()));
    }
}
