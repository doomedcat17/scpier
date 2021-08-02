package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.SCPierApiException;
import com.doomedcat17.scpier.exception.SCPierApiInternalException;
import com.doomedcat17.scpier.exception.SCPierResourcesInitializationException;
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFound;
import com.doomedcat17.scpier.mapper.scp.DefaultScpWikiContentMapper;
import com.doomedcat17.scpier.mapper.scp.ScpWikiContentMapper;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.WikiContentProvider;

public class ScpFoundationDataProvider {

    private final WikiContentProvider wikiContentProvider;

    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch) throws SCPierApiException {
        return getScpWikiData(articleName, scpBranch, SCPTranslation.ORIGINAL);
    }

    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPierApiException {
        try {
            WikiContent wikiContent = getPageContent(articleName, scpBranch, scpTranslation);
            ScpWikiContentMapper scpWikiContentMapper = new DefaultScpWikiContentMapper();
            ScpWikiData scpWikiData = scpWikiContentMapper.mapToScp(wikiContent);
            scpWikiData.setTags(wikiContent.getTags());
            scpWikiData.setScpBranch(scpBranch);
            scpWikiData.setScpTranslation(scpTranslation);
            return scpWikiData;
        } catch (RuntimeException e) {
            throw new SCPierApiInternalException(articleName, scpBranch, scpTranslation, e);
        }
    }

    private WikiContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound {
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
