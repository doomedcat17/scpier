package com.doomedcat17.scpier.page.html.document.interpreter;

import com.doomedcat17.scpier.exception.HTMLDocumentInterpreterException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.cleaner.WikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.provider.IframeContentProvider;
import com.doomedcat17.scpier.page.html.document.provider.ScriptedWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.redirection.WikiRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapper;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Optional;

public class WikiPageInterpreter {

    private final WikiContentCleaner wikiContentCleaner;

    private final WikiRedirectionHandler wikiRedirectionHandler;

    private final PageTagsScrapper pageTagsScrapper;

    public void mapContent(WikiContent wikiContent)  {
        try {
            Preset preset;
            if (wikiContent.getPreset() != null) {
                preset = wikiContent.getPreset();
                wikiContentCleaner.additionalRemovalDefinitions(preset.getRemovalDefinitions());
            } else preset = new Preset();
            Element content = wikiContent.getContent().getElementById("page-content");
            Optional<Element> redirectionElement = wikiRedirectionHandler.provideRedirectedContent(content);
            if (redirectionElement.isPresent())
                content = wikiRedirectionHandler.getRedirectionContent(redirectionElement.get(), wikiContent.getSourceUrl());
            wikiContent.setName(wikiContent.getContent().getElementById("page-title").text());
            Optional<List<String>> tagNames = pageTagsScrapper.scrapPageTags(wikiContent.getContent());
            tagNames.ifPresent(wikiContent::setTags);
            wikiContent.setContent(content);
            if (!content.getElementsByTag("iframe").isEmpty()) {
                IframeContentProvider iframeContentProvider = new IframeContentProvider(new ScriptedWikiPageProvider(), wikiContentCleaner);
                iframeContentProvider.provideIframesContent(wikiContent, preset);
            }
            wikiContentCleaner.clearContentAndUnpackBlocks(content);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HTMLDocumentInterpreterException(e.getMessage());
        }
    }

    public WikiPageInterpreter(WikiContentCleaner wikiContentCleaner, WikiRedirectionHandler wikiRedirectionHandler, PageTagsScrapper pageTagsScrapper) {
        this.wikiContentCleaner = wikiContentCleaner;
        this.wikiRedirectionHandler = wikiRedirectionHandler;
        this.pageTagsScrapper = pageTagsScrapper;
    }
}
