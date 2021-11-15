package com.doomedcat17.scpier.page.html.document.interpreter;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.exception.page.html.document.interpreter.WikiPageInterpreterException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.author.AuthorScraper;
import com.doomedcat17.scpier.page.html.document.cleaner.DefaultWikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.cleaner.WikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.executor.PresetExecutor;
import com.doomedcat17.scpier.page.html.document.provider.DefaultWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.IframeContentProvider;
import com.doomedcat17.scpier.page.html.document.provider.ScriptedWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.doomedcat17.scpier.page.html.document.redirection.WikiRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.revision.LastRevisionTimestampProvider;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapper;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapperImpl;
import com.gargoylesoftware.htmlunit.WebClient;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class WikiPageInterpreter {
    private final WebClient webClient;

    public void mapContent(WikiContent wikiContent) {
        try {
            WikiContentCleaner wikiContentCleaner = new DefaultWikiContentCleaner(ResourcesProvider.getRemovalDefinitions());
            WikiPageProvider wikiPageProvider = new DefaultWikiPageProvider();
            WikiRedirectionHandler wikiRedirectionHandler = new WikiRedirectionHandler(wikiPageProvider, ResourcesProvider.getRedirectionDefinitions());
            Preset preset;
            if (wikiContent.getPreset() != null) {
                preset = wikiContent.getPreset();
                wikiContentCleaner.additionalRemovalDefinitions(preset.getRemovalDefinitions());
            } else preset = new Preset();
            Element content = wikiContent.getContent().getElementById("page-content");
            Optional<Element> redirectionElement = wikiRedirectionHandler.provideRedirectedContent(content);
            if (redirectionElement.isPresent()) {
                content = wikiRedirectionHandler.getRedirectionContent(redirectionElement.get(), wikiContent.getContentSource());
                Timestamp lastRevision = LastRevisionTimestampProvider.getLastRevisionTimestamp(content);
                wikiContent.setContent(content);
                if (lastRevision.after(wikiContent.getLastRevisionTimestamp()))
                    wikiContent.setLastRevisionTimestamp(lastRevision);
            }
            setWikiContentTitle(wikiContent);
            setWikiContentTags(wikiContent);
            setWikiContentAuthor(wikiContent);
            wikiContent.setContent(wikiContent.getContent().getElementById("page-content"));
            Elements iframes = content.select("iframe");
            if (!iframes.isEmpty()) {
                IframeContentProvider iframeContentProvider = new IframeContentProvider(wikiContentCleaner, new ScriptedWikiPageProvider(webClient), new PresetExecutor(webClient));
                iframeContentProvider.provideIframesContent(wikiContent, preset);
            }
            wikiContentCleaner.removeTrashNodes(wikiContent.getContent());
        } catch (Exception e) {
            throw new WikiPageInterpreterException(e);
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
            wikiContent.setName(pageTitleElement.text());
        }
    }

    private void setWikiContentAuthor(WikiContent wikiContent) throws IOException {
        AuthorScraper authorScraper = new AuthorScraper(webClient);
        String authorName = authorScraper.scrap(wikiContent.getContentSource());
        wikiContent.setAuthor(authorName);
    }

    public WikiPageInterpreter(WebClient webClient) {
        this.webClient = webClient;
    }
}
