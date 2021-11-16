package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPLanguage;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.MissingLanguageException;
import com.doomedcat17.scpier.exception.SCPierApiException;
import com.doomedcat17.scpier.exception.SCPierApiInternalException;
import com.doomedcat17.scpier.exception.SCPierResourcesInitializationException;
import com.doomedcat17.scpier.exception.data.SCPWikiEmptyContentException;
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
        if (scpBranch.equals(SCPBranch.NORDIC)) throw new MissingLanguageException("SCPLanguage is required for Nordic Branch");
        return getScpWikiData(articleName, scpBranch, SCPLanguage.getById(scpBranch.identifier));
    }

    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch, SCPLanguage scpLanguage) throws SCPierApiException {
        try {
            WikiContent wikiContent = wikiContentProvider.getPageContent(articleName, scpBranch, scpLanguage, webClient);
            return mapWikiContent(wikiContent, scpBranch, scpLanguage);
        } catch (RuntimeException | RevisionDateException e) {
            throw new SCPierApiInternalException(articleName, scpBranch, scpLanguage, e);
        }
    }

    private ScpWikiData mapWikiContent(WikiContent wikiContent, SCPBranch scpBranch, SCPLanguage scpLanguage) throws SCPWikiEmptyContentException {
        ScpWikiContentMapper scpWikiContentMapper = new DefaultScpWikiContentMapper();
        ScpWikiData scpWikiData = scpWikiContentMapper.mapWikiContent(wikiContent);
        if (scpWikiData.getContent().isEmpty())
            throw new SCPWikiEmptyContentException("Article content is empty!", new NullPointerException());
        scpWikiData.setTags(wikiContent.getTags());
        scpWikiData.setBranch(scpBranch);
        scpWikiData.setLanguage(scpLanguage);
        return scpWikiData;
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
