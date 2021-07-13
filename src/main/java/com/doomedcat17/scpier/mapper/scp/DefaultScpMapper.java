package com.doomedcat17.scpier.mapper.scp;

import com.doomedcat17.scpier.data.scp.ScpObject;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.exception.ScpMapperException;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import org.jsoup.nodes.Element;

public class DefaultScpMapper implements ScpMapper {
    @Override
    public ScpObject mapToScp(PageContent pageContent)  {
        try {
            Element content = pageContent.getContent();
            ScpObject scpObject = new ScpObject();
            scpObject.setObjectName(pageContent.getName());
            scpObject.setSource(pageContent.getSourceUrl());
            mapScp(scpObject, content);
            return scpObject;
        } catch (ElementScrapperException e) {
            throw new ScpMapperException(e.getMessage());
        }
    }
    private void mapScp(ScpObject scpObject, Element content)  {
        scpObject.setContent(ElementContentScrapper.scrapContent(content, scpObject.getSource()));
    }
}
