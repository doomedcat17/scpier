package com.doomedcat17.scpier.mapper.scp;

import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.mapper.ScpMapperException;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import org.jsoup.nodes.Element;

public class DefaultScpWikiContentMapper implements ScpWikiContentMapper {
    @Override
    public ScpWikiData mapToScp(WikiContent wikiContent)  {
        try {
            ScpWikiData scpWikiData = new ScpWikiData();
            scpWikiData.setTitle(wikiContent.getName());
            scpWikiData.setSource(wikiContent.getSourceUrl());
            mapScp(scpWikiData, wikiContent.getContent());
            return scpWikiData;
        } catch (ElementScraperException e) {
            throw new ScpMapperException(e);
        }
    }
    private void mapScp(ScpWikiData scpWikiData, Element content)  {
        scpWikiData.setContent(ElementContentScraper.scrapContent(content, scpWikiData.getSource()));
    }
}
