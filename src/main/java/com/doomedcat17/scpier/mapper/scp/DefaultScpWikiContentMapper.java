package com.doomedcat17.scpier.mapper.scp;

import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.exception.ScpMapperException;
import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import org.jsoup.nodes.Element;

public class DefaultScpWikiContentMapper implements ScpWikiContentMapper {
    @Override
    public ScpWikiData mapToScp(PageContent pageContent)  {
        try {
            ScpWikiData scpWikiData = new ScpWikiData();
            scpWikiData.setTitle(pageContent.getName());
            scpWikiData.setSource(pageContent.getSourceUrl());
            mapScp(scpWikiData, pageContent.getContent());
            return scpWikiData;
        } catch (ElementScrapperException e) {
            e.printStackTrace();
            throw new ScpMapperException(e.getMessage());
        }
    }
    private void mapScp(ScpWikiData scpWikiData, Element content)  {
        scpWikiData.setContent(ElementContentScrapper.scrapContent(content, scpWikiData.getSource()));
    }
}
