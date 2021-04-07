package com.doomedcat17.scpier.mapper.scp_mappers;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.scp.ScpObject;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import org.jsoup.nodes.Element;

public class DefaultScpMapper implements ScpMapper {
    @Override
    public ScpObject mapToScp(PageContent pageContent) {
        Element content = pageContent.getContent();
        ScpObject scpObject = new ScpObject();
        scpObject.setObjectName(pageContent.getName());
        scpObject.setSource(pageContent.getSourceUrl());
        mapScp(scpObject, content);
        return scpObject;
    }
    private void mapScp(ScpObject scpObject, Element content) {
        ElementScrapper elementScrapper = new ElementScrapper(scpObject.getSource()) {
            @Override
            public ContentNode<?> scrapElement(Element element) {
                return null;
            }
        };
        scpObject.setContent(elementScrapper.scrapContent(content));
    }
}
