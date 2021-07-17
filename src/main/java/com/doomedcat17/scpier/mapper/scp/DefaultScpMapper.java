package com.doomedcat17.scpier.mapper.scp;

import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.exception.ScpMapperException;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import org.jsoup.nodes.Element;

public class DefaultScpMapper implements ScpMapper {
    @Override
    public ScpWikiData mapToScp(PageContent pageContent)  {
        try {
            Element content = pageContent.getContent();
            ScpWikiData scpWikiData = new ScpWikiData();
            scpWikiData.setTitle(pageContent.getName());
            scpWikiData.setSource(pageContent.getSourceUrl());
            mapScp(scpWikiData, content);
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
