package com.doomedcat17.scpier.page.html.document.interpreter;

import com.doomedcat17.scpier.exception.HTMLDocumentInterpreterException;
import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.cleaner.DocumentContentCleaner;
import com.doomedcat17.scpier.page.html.document.js.ScriptedWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.IframeHTMLProvider;
import com.doomedcat17.scpier.page.html.document.redirection.HTMLRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapper;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Optional;

public class HTMLDocumentInterpreter {

    private final DocumentContentCleaner documentContentCleanerImpl;

    private final HTMLRedirectionHandler htmlRedirectionHandler;

    private final PageTagsScrapper pageTagsScrapper;

    public void mapContent(PageContent pageContent)  {
        try {
            Element content = pageContent.getContent().getElementById("page-content");
            Optional<Element> redirectionElement = htmlRedirectionHandler.checkForRedirection(content);
            if (redirectionElement.isPresent())
                content = htmlRedirectionHandler.getRedirectionContent(redirectionElement.get(), pageContent.getSourceUrl());
            pageContent.setName(pageContent.getContent().getElementById("page-title").text());
            Optional<List<String>> tagNames = pageTagsScrapper.scrapPageTags(pageContent.getContent());
            tagNames.ifPresent(pageContent::setTags);
            pageContent.setContent(content);
            if (!content.getElementsByTag("iframe").isEmpty()) {
                IframeHTMLProvider iframeHTMLProvider = new IframeHTMLProvider(new ScriptedWikiPageProvider(), documentContentCleanerImpl);
                iframeHTMLProvider.provideIframesContent(pageContent);
            }
            documentContentCleanerImpl.clearContentAndUnpackBlocks(content);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HTMLDocumentInterpreterException(e.getMessage());
        }
    }

    public HTMLDocumentInterpreter(DocumentContentCleaner documentContentCleanerImpl, HTMLRedirectionHandler htmlRedirectionHandler, PageTagsScrapper pageTagsScrapper) {
        this.documentContentCleanerImpl = documentContentCleanerImpl;
        this.htmlRedirectionHandler = htmlRedirectionHandler;
        this.pageTagsScrapper = pageTagsScrapper;
    }
}
