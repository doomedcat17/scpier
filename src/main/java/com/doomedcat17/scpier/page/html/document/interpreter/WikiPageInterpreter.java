package com.doomedcat17.scpier.page.html.document.interpreter;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.exception.page.html.document.interpreter.WikiPageInterpreterException;
import com.doomedcat17.scpier.exception.page.html.document.provider.IframeContentProviderException;
import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.cleaner.DefaultWikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.cleaner.WikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.executor.PresetExecutor;
import com.doomedcat17.scpier.page.html.document.provider.DefaultWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.iframe.IframeContentProvider;
import com.doomedcat17.scpier.page.html.document.redirection.WikiRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.revision.LastRevisionDateScraper;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapper;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapperImpl;
import com.gargoylesoftware.htmlunit.WebClient;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class WikiPageInterpreter {
    private final WebClient webClient;

    private final WikiPageProvider wikiPageProvider;

    public void mapContent(WikiContent wikiContent) {
        try {
            WikiContentCleaner wikiContentCleaner = new DefaultWikiContentCleaner(ResourcesProvider.getRemovalDefinitions());
            Preset preset;
            if (wikiContent.getPreset() != null) {
                preset = wikiContent.getPreset();
                wikiContentCleaner.additionalRemovalDefinitions(preset.getRemovalDefinitions());
            }
            handleRedirections(wikiContent);
            setWikiContentTitle(wikiContent);
            setWikiContentTags(wikiContent);
            wikiContent.setContent(wikiContent.getContent().getElementById("page-content"));
            handleIframes(wikiContent, wikiContentCleaner);
            wikiContentCleaner.removeTrashNodes(wikiContent.getContent());
        } catch (Exception e) {
            throw new WikiPageInterpreterException(e);
        }
    }

    private void handleRedirections(WikiContent wikiContent) throws IOException, RevisionDateException {
        WikiRedirectionHandler wikiRedirectionHandler = new WikiRedirectionHandler(wikiPageProvider, ResourcesProvider.getRedirectionDefinitions());
        Element content = wikiContent.getContent().getElementById("page-content");
        Optional<Element> redirectionElement = wikiRedirectionHandler.provideRedirectedContent(content);
        if (redirectionElement.isPresent()) {
            content = wikiRedirectionHandler.getRedirectionContent(redirectionElement.get(), wikiContent.getContentSource());
            LastRevisionDateScraper lastRevisionDateScraper = new LastRevisionDateScraper();
            LocalDateTime lastRevision = lastRevisionDateScraper.scrapDate(content);
            wikiContent.setContent(content);
            if (lastRevision.isAfter(wikiContent.getLastRevisionDate()))
                wikiContent.setLastRevisionDate(lastRevision);
        }
    }

    private void handleIframes(WikiContent wikiContent, WikiContentCleaner wikiContentCleaner) throws IframeContentProviderException {
        Elements iframes = wikiContent.getContent().select("iframe");
        if (!iframes.isEmpty()) {
            IframeContentProvider iframeContentProvider = new IframeContentProvider(wikiContentCleaner, new DefaultWikiPageProvider(webClient), new PresetExecutor(webClient));
            iframeContentProvider.includeIframesContent(wikiContent);
        }
    }

    private void setWikiContentTags(WikiContent wikiContent) {
        PageTagsScrapper pageTagsScrapper = new PageTagsScrapperImpl();
        Optional<List<String>> tagNames = pageTagsScrapper.scrapPageTags(wikiContent.getContent());
        tagNames.ifPresent(wikiContent::setTags);
    }

    private void setWikiContentTitle(WikiContent wikiContent) {
        Element pageTitleElement = wikiContent.getContent().getElementById("page-title");
        if (pageTitleElement != null) {
            wikiContent.setTitle(pageTitleElement.text());
        }
    }

    public WikiPageInterpreter(WebClient webClient, WikiPageProvider wikiPageProvider) {
        this.webClient = webClient;
        this.wikiPageProvider = wikiPageProvider;
    }
}
