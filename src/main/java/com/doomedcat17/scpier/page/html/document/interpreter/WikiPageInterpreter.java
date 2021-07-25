package com.doomedcat17.scpier.page.html.document.interpreter;

import com.doomedcat17.scpier.exception.HTMLDocumentInterpreterException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.cleaner.WikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.provider.IframeHTMLProvider;
import com.doomedcat17.scpier.page.html.document.provider.ScriptedWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.redirection.WikiRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapper;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Optional;

public class WikiPageInterpreter {

    private final WikiContentCleaner wikiContentCleanerImpl;

    private final WikiRedirectionHandler wikiRedirectionHandler;

    private final PageTagsScrapper pageTagsScrapper;

    public void mapContent(WikiContent wikiContent)  {
        try {
            Element content = wikiContent.getContent().getElementById("page-content");
            Optional<Element> redirectionElement = wikiRedirectionHandler.checkForRedirection(content);
            if (redirectionElement.isPresent())
                content = wikiRedirectionHandler.getRedirectionContent(redirectionElement.get(), wikiContent.getSourceUrl());
            wikiContent.setName(wikiContent.getContent().getElementById("page-title").text());
            Optional<List<String>> tagNames = pageTagsScrapper.scrapPageTags(wikiContent.getContent());
            tagNames.ifPresent(wikiContent::setTags);
            wikiContent.setContent(content);
            if (!content.getElementsByTag("iframe").isEmpty()) {
                IframeHTMLProvider iframeHTMLProvider = new IframeHTMLProvider(new ScriptedWikiPageProvider(), wikiContentCleanerImpl);
                iframeHTMLProvider.provideIframesContent(wikiContent);
            }
            wikiContentCleanerImpl.clearContentAndUnpackBlocks(content);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HTMLDocumentInterpreterException(e.getMessage());
        }
    }

    public WikiPageInterpreter(WikiContentCleaner wikiContentCleanerImpl, WikiRedirectionHandler wikiRedirectionHandler, PageTagsScrapper pageTagsScrapper) {
        this.wikiContentCleanerImpl = wikiContentCleanerImpl;
        this.wikiRedirectionHandler = wikiRedirectionHandler;
        this.pageTagsScrapper = pageTagsScrapper;
    }
}
