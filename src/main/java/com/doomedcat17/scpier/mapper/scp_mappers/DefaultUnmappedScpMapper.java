package com.doomedcat17.scpier.mapper.scp_mappers;

import com.doomedcat17.scpier.data.scp_object.ScpObject;
import com.doomedcat17.scpier.data.scp_object.UnmappedScpObject;
import com.doomedcat17.scpier.page_content.PageContent;
import com.doomedcat17.scpier.scrapper.htmlscrappers.line.LineScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import org.jsoup.nodes.Element;

public class DefaultUnmappedScpMapper implements ScpMapper {
    @Override
    public ScpObject mapToScp(PageContent pageContent, TitleResolver titleResolver) {
        Element content = pageContent.getContent();
        ScpObject scpObject = new UnmappedScpObject();
        scpObject.setObjectName(pageContent.getName());
        scpObject.setSource(pageContent.getSourceUrl());
        mapScp(scpObject, content, titleResolver);
        return scpObject;
    }

    private void mapScp(ScpObject scpObject, Element content, TitleResolver titleResolver) {
        LineScrapper lineScrapper = new LineScrapper(scpObject.getSource(), titleResolver);
        scpObject.setContent(lineScrapper.scrapContent(content));
    }
}
