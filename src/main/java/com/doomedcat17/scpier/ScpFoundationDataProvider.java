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
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFoundException;
import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import com.doomedcat17.scpier.mapper.scp.DefaultScpWikiContentMapper;
import com.doomedcat17.scpier.mapper.scp.ScpWikiContentMapper;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.WikiContentProvider;
import com.doomedcat17.scpier.page.webclients.RateLimitedWebClient;
import com.gargoylesoftware.htmlunit.WebClient;

import java.util.Objects;

public class ScpFoundationDataProvider {

    private final WikiContentProvider wikiContentProvider;
    private long timePeriod = 60;
    private long requestCap = 240;
    private WebClient customWebClient;

    public ScpFoundationDataProvider() {
        if (!ResourcesProvider.isInitialized()) {
            try {
                ResourcesProvider.initResources();
            } catch (Exception e) {
                throw new SCPierResourcesInitializationException(e);
            }
        }
        this.wikiContentProvider = new WikiContentProvider();
    }

    /**
     * Creates an instance of {@link com.doomedcat17.scpier.ScpFoundationDataProvider} with custom request rate limiter.
     *
     * @param timePeriod a time period (in seconds) in which requests will be limited.
     * @param requestCap a max number of requests in timePeriod.
     * @return an instance of {@link com.doomedcat17.scpier.ScpFoundationDataProvider} with defined request rate limitation.
     */

    public static ScpFoundationDataProvider createWithCustomRequestRateLimit(long timePeriod, long requestCap) {
        return new ScpFoundationDataProvider(timePeriod, requestCap);
    }

    private ScpFoundationDataProvider(long timePeriod, long requestCap) {
        if (!ResourcesProvider.isInitialized()) {
            try {
                ResourcesProvider.initResources();
            } catch (Exception e) {
                throw new SCPierResourcesInitializationException(e);
            }
        }
        this.wikiContentProvider = new WikiContentProvider();
        this.requestCap = requestCap;
        this.timePeriod = timePeriod;
    }

    /**
     * Creates an instance of {@link com.doomedcat17.scpier.ScpFoundationDataProvider} with provided {@link com.gargoylesoftware.htmlunit.WebClient}.
     *
     * @param webClient an instance of {@link com.gargoylesoftware.htmlunit.WebClient}.
     * @return an instance of {@link com.doomedcat17.scpier.ScpFoundationDataProvider} with provided WebClient.
     */
    public static ScpFoundationDataProvider createWithCustomWebClient(WebClient webClient) {
        return new ScpFoundationDataProvider(webClient);
    }

    private ScpFoundationDataProvider(WebClient customWebClient) {
        if (!ResourcesProvider.isInitialized()) {
            try {
                ResourcesProvider.initResources();
            } catch (Exception e) {
                throw new SCPierResourcesInitializationException(e);
            }
        }
        this.wikiContentProvider = new WikiContentProvider();
        this.customWebClient = customWebClient;
    }


    /**
     * Provides an article from the SCP Foundation Wiki
     *
     * @param articleName a name of the article. It corresponds to article's URL name.
     * @param scpBranch   an instance of {@link com.doomedcat17.scpier.data.scp.SCPBranch}. Defines original branch of the article.
     * @return a content of the article as {@link com.doomedcat17.scpier.data.scp.ScpWikiData} object in its original language.
     * @throws SCPWikiContentNotFoundException if article hasn't been found.
     * @throws SCPWikiEmptyContentException    if content of the article is empty.
     * @throws MissingLanguageException        for {@link com.doomedcat17.scpier.data.scp.SCPBranch} NORDIC, due to branch's multilingual nature.
     * @throws SCPierApiInternalException      if any internal exception occurs.
     */

    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch) throws SCPierApiException {
        if (scpBranch.equals(SCPBranch.NORDIC))
            throw new MissingLanguageException("SCPLanguage is required for Nordic Branch");
        return getScpWikiData(articleName, scpBranch, SCPLanguage.getById(scpBranch.identifier));
    }

    /**
     * Provides an article from the SCP Foundation Wiki
     *
     * @param articleName a name of the article. It corresponds to article's URL name.
     * @param scpBranch   an instance of {@link com.doomedcat17.scpier.data.scp.SCPBranch}. Defines original branch of the article.
     * @param scpLanguage an instance of {@link com.doomedcat17.scpier.data.scp.SCPLanguage}. Defines a language of the article.
     * @return a content of the article as {@link com.doomedcat17.scpier.data.scp.ScpWikiData} object in its original language.
     * @throws SCPWikiContentNotFoundException if article hasn't been found.
     * @throws SCPWikiEmptyContentException    if content of the article is empty.
     * @throws MissingLanguageException        for {@link com.doomedcat17.scpier.data.scp.SCPBranch} NORDIC, due to branch's multilingual nature.
     * @throws SCPierApiInternalException      if any internal exception occurs.
     */
    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch, SCPLanguage scpLanguage) throws SCPierApiException {
        WebClient webClient;
        if (Objects.isNull(customWebClient)) {
            webClient = new RateLimitedWebClient(timePeriod, requestCap);
        } else webClient = customWebClient;
        try (webClient) {
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


}
