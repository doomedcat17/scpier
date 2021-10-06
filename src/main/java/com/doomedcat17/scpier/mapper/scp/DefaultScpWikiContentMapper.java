package com.doomedcat17.scpier.mapper.scp;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.ListNode;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.mapper.ScpMapperException;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import org.jsoup.nodes.Element;

import java.util.List;

public class DefaultScpWikiContentMapper implements ScpWikiContentMapper {
    @Override
    public ScpWikiData mapWikiContent(WikiContent wikiContent)  {
        try {
            ScpWikiData scpWikiData = new ScpWikiData();
            scpWikiData.setTitle(wikiContent.getName());
            scpWikiData.setOriginalSource(wikiContent.getOriginalSourceUrl());
            scpWikiData.setTranslationSource(wikiContent.getTranslationSourceUrl());
            scpWikiData.setLastRevisionTimestamp(wikiContent.getLastRevisionTimestamp());
            mapScp(scpWikiData, wikiContent.getContent());
            unpackDivs(scpWikiData);
            return scpWikiData;
        } catch (ElementScraperException e) {
            throw new ScpMapperException(e);
        }
    }

    private void unpackDivs(ScpWikiData scpWikiData) {
        if (scpWikiData.getContent().size() <= 4) {
            for (int i = 0; i < scpWikiData.getContent().size(); i++) {
                ContentNode<?> contentNode = scpWikiData.getContent().get(i);
                if (contentNode.getContentNodeType().equals(ContentNodeType.DIV)) {
                    ListNode<ContentNode<?>> divNode = (ListNode<ContentNode<?>>) contentNode;
                    List<ContentNode<?>> divContent = divNode.getContent();
                    scpWikiData.getContent().addAll(i, divContent);
                    scpWikiData.getContent().remove(contentNode);
                }
            }
        }
    }

    private void mapScp(ScpWikiData scpWikiData, Element content)  {
        scpWikiData.setContent(ElementContentScraper.scrapContent(content, scpWikiData.getContentSource()));
    }
}
