package com.doomedcat17.scpier.page_content.html.document;

import com.doomedcat17.scpier.page_content.PageContent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HTMLDocumentInterpreter {

    private final DocumentContentCleaner documentContentCleanerImpl;

    private final HTMLRedirectionHandler htmlRedirectionHandler;

    private final PageTagsScrapper pageTagsScrapper;

    public void mapDocument(Document contentDocument, PageContent pageContent) throws IOException {
        Element content = contentDocument.getElementById("page-content");
        Optional<Element> redirectionElement = htmlRedirectionHandler.checkForRedirection(content);
        if (redirectionElement.isPresent()) content = htmlRedirectionHandler.getRedirectionContent(redirectionElement.get(), pageContent.getSourceUrl());
        pageContent.setName(contentDocument.getElementById("page-title").text());
        Optional<List<String>> tagNames = pageTagsScrapper.scrapPageTags(contentDocument);
        tagNames.ifPresent(pageContent::setTags);
        pageContent.setContent(content);
        documentContentCleanerImpl.clearContentAndUnpackBlocks(content);
        if (!content.select(":root iframe").isEmpty()) {
            IframeProvider iframeProvider = new IframeProvider(new HTMLDocumentProvider(), documentContentCleanerImpl);
            iframeProvider.provideIframesContent(pageContent);
        }
    }

    public HTMLDocumentInterpreter(DocumentContentCleaner documentContentCleanerImpl, HTMLRedirectionHandler htmlRedirectionHandler, PageTagsScrapper pageTagsScrapper) {
        this.documentContentCleanerImpl = documentContentCleanerImpl;
        this.htmlRedirectionHandler = htmlRedirectionHandler;
        this.pageTagsScrapper = pageTagsScrapper;
    }
}
