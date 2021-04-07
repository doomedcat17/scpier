package com.doomedcat17.scpier.pagecontent;

import com.doomedcat17.scpier.pagecontent.html.document.*;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PageContentProvider {

    public PageContent getPageContent(String url) throws IOException {
        HTMLDocumentProvider htmlDocumentProvider = new HTMLDocumentProvider();
        HTMLDocumentInterpreter htmlDocumentInterpreter =
                new HTMLDocumentInterpreter(new DocumentContentCleanerImpl(),
                        new HTMLRedirectionHandler(htmlDocumentProvider),
                        new PageTagsScrapperImpl()
                );
        Document pageDocument = htmlDocumentProvider.getWebpageContent(url);
        PageContent pageContent = new PageContent();
        pageContent.setSourceUrl(url);
        htmlDocumentInterpreter.mapDocument(pageDocument, pageContent);
        return pageContent;
    }
}
