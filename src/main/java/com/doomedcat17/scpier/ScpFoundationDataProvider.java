package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.SCPierApiException;
import com.doomedcat17.scpier.exception.SCPierApiInternalException;
import com.doomedcat17.scpier.exception.SCPierResourcesInitializationException;
import com.doomedcat17.scpier.exception.data.SCPWikiEmptyContentException;
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFound;
import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import com.doomedcat17.scpier.mapper.scp.DefaultScpWikiContentMapper;
import com.doomedcat17.scpier.mapper.scp.ScpWikiContentMapper;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.WikiContentProvider;

import java.sql.Timestamp;

public class ScpFoundationDataProvider {

    private final WikiContentProvider wikiContentProvider;

    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch) throws SCPierApiException {
        return getScpWikiData(articleName, scpBranch, SCPTranslation.ORIGINAL);
    }

    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPierApiException {
        try {
            WikiContent wikiContent = getPageContent(articleName, scpBranch, scpTranslation);
            return mapWikiContent(wikiContent, scpBranch, scpTranslation);
        } catch (RuntimeException | RevisionDateException e) {
            throw new SCPierApiInternalException(articleName, scpBranch, scpTranslation, e);
        }
    }

    private ScpWikiData mapWikiContent(WikiContent wikiContent, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiEmptyContentException {
        ScpWikiContentMapper scpWikiContentMapper = new DefaultScpWikiContentMapper();
        ScpWikiData scpWikiData = scpWikiContentMapper.mapWikiContent(wikiContent);
        if (scpWikiData.getContent().isEmpty())
            throw new SCPWikiEmptyContentException("Article content is empty!", new NullPointerException());
        scpWikiData.setTags(wikiContent.getTags());
        scpWikiData.setScpBranch(scpBranch);
        scpWikiData.setScpTranslation(scpTranslation);
        return scpWikiData;
    }

    public void updateScpWikiData(ScpWikiData scpWikiData) throws SCPierApiException {
        String articleName = scpWikiData.getTitle();
        try {
            String source = scpWikiData.getSource();
            articleName = source.substring(source.lastIndexOf('/') + 1);
            WikiContent wikiContent = getPageContent(articleName, scpWikiData.getScpBranch(), scpWikiData.getScpTranslation());
            if (wikiContent.getLastRevisionTimestamp().after(scpWikiData.getLastRevisionTimestamp())) {
                ScpWikiData updatedScpWikiData = mapWikiContent(wikiContent, scpWikiData.getScpBranch(), scpWikiData.getScpTranslation());
                scpWikiData.setTitle(updatedScpWikiData.getTitle());
                scpWikiData.setContent(updatedScpWikiData.getContent());
                scpWikiData.setTags(updatedScpWikiData.getTags());
                scpWikiData.setLastRevisionTimestamp(updatedScpWikiData.getLastRevisionTimestamp());
            }
        } catch (RuntimeException | RevisionDateException e) {
            throw new SCPierApiInternalException(articleName, scpWikiData.getScpBranch(), scpWikiData.getScpTranslation(), e);
        }
    }

    public Timestamp getLastRevisionTimestamp(String articleName, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPierApiException {
        try {
            return getPageContent(articleName, scpBranch, scpTranslation).getLastRevisionTimestamp();
        } catch (RuntimeException | RevisionDateException e) {
            throw new SCPierApiInternalException(articleName, scpBranch, scpTranslation, e);
        }
    }

    public Timestamp getLastRevisionTimestamp(String articleName, SCPBranch scpBranch) throws SCPierApiException {
        return getLastRevisionTimestamp(articleName, scpBranch, SCPTranslation.ORIGINAL);
    }

    private WikiContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound, RevisionDateException {
        WikiContent wikiContent = wikiContentProvider.getPageContent(name, scpBranch, scpTranslation);
        if (scpTranslation.equals(SCPTranslation.ORIGINAL)) {
            wikiContent.setTranslationIdentifier(scpBranch.identifier);
        } else wikiContent.setTranslationIdentifier(scpTranslation.identifier);
        return wikiContent;
    }

    public ScpFoundationDataProvider() {
        try {
            ResourcesProvider.initResources();
            this.wikiContentProvider = new WikiContentProvider();
        } catch (Exception e) {
            throw new SCPierResourcesInitializationException(e);
        }
    }
}
