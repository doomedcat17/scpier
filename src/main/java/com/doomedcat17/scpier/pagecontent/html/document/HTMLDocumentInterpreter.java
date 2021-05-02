package com.doomedcat17.scpier.pagecontent.html.document;

import com.doomedcat17.scpier.pagecontent.PageContent;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HTMLDocumentInterpreter {

    private final DocumentContentCleaner documentContentCleanerImpl;

    private final HTMLRedirectionHandler htmlRedirectionHandler;

    private final PageTagsScrapper pageTagsScrapper;

    public void mapContent(PageContent pageContent) throws IOException {
        Element content = pageContent.getContent().getElementById("page-content");
        Optional<Element> redirectionElement = htmlRedirectionHandler.checkForRedirection(content);
        if (redirectionElement.isPresent()) content = htmlRedirectionHandler.getRedirectionContent(redirectionElement.get(), pageContent.getSourceUrl());
        pageContent.setName(pageContent.getContent().getElementById("page-title").text());
        Optional<List<String>> tagNames = pageTagsScrapper.scrapPageTags(pageContent.getContent());
        tagNames.ifPresent(pageContent::setTags);
        pageContent.setContent(content);
        if (!content.getElementsByTag("iframe").isEmpty()) {
            IframeProvider iframeProvider = new IframeProvider(new DefaultHTMLDocumentProvider(), documentContentCleanerImpl);
            iframeProvider.provideIframesContent(pageContent);
        }
        documentContentCleanerImpl.clearContentAndUnpackBlocks(content);
    }

    public HTMLDocumentInterpreter(DocumentContentCleaner documentContentCleanerImpl, HTMLRedirectionHandler htmlRedirectionHandler, PageTagsScrapper pageTagsScrapper) {
        this.documentContentCleanerImpl = documentContentCleanerImpl;
        this.htmlRedirectionHandler = htmlRedirectionHandler;
        this.pageTagsScrapper = pageTagsScrapper;
    }
}
