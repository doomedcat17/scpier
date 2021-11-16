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
import com.doomedcat17.scpier.page.webclients.NicelyLimitedWebClient;
import com.doomedcat17.scpier.page.webclients.RateLimitedWebClient;
import com.gargoylesoftware.htmlunit.WebClient;
public class ScpFoundationDataProvider {

    private WikiContentProvider wikiContentProvider;

    private final WebClient webClient;

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


    private WikiContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound, RevisionDateException {
        WikiContent wikiContent = wikiContentProvider.getPageContent(name, scpBranch, scpTranslation, webClient);
        if (scpTranslation.equals(SCPTranslation.ORIGINAL)) {
            wikiContent.setTranslationIdentifier(scpBranch.identifier);
        } else wikiContent.setTranslationIdentifier(scpTranslation.identifier);
        return wikiContent;
    }

    public ScpFoundationDataProvider() {
        if (!ResourcesProvider.isInitialized()) {
            try {
                ResourcesProvider.initResources();
            } catch (Exception e) {
                throw new SCPierResourcesInitializationException(e);
            }
        }
        this.wikiContentProvider = new WikiContentProvider();
        this.webClient = new NicelyLimitedWebClient();
    }

    public ScpFoundationDataProvider(long timePeriod, long requestCap) {
        if (!ResourcesProvider.isInitialized()) {
            try {
                ResourcesProvider.initResources();
            } catch (Exception e) {
                throw new SCPierResourcesInitializationException(e);
            }
        }
        this.wikiContentProvider = new WikiContentProvider();
        this.webClient = new RateLimitedWebClient(timePeriod, requestCap);
    }

    public ScpFoundationDataProvider(WebClient webClient) {
        if (!ResourcesProvider.isInitialized()) {
            try {
                ResourcesProvider.initResources();
            } catch (Exception e) {
                throw new SCPierResourcesInitializationException(e);
            }
        }
        this.wikiContentProvider = new WikiContentProvider();
        this.webClient = webClient;
    }

}
