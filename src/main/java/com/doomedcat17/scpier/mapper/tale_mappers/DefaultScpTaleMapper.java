package com.doomedcat17.scpier.mapper.tale_mappers;

import com.doomedcat17.scpier.data.tale.ScpTale;
import com.doomedcat17.scpier.page_content.PageContent;
import com.doomedcat17.scpier.scrapper.htmlscrappers.line.LineScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import org.jsoup.nodes.Element;

public class DefaultScpTaleMapper implements ScpTaleMapper {
    @Override
    public ScpTale mapToTale(PageContent pageContent, TitleResolver titleResolver) {
        ScpTale scpTale = new ScpTale();
        scpTale.setTitle(pageContent.getName());
        scpTale.setSource(pageContent.getSourceUrl());
        mapTale(scpTale, pageContent.getContent(), titleResolver);
        return scpTale;
    }

    private void mapTale(ScpTale scpTale, Element pageContentElement, TitleResolver titleResolver) {
        LineScrapper lineScrapper = new LineScrapper(scpTale.getSource(), titleResolver);
        scpTale.setContent(lineScrapper.scrapElementInnerContent(pageContentElement));
    }
}
