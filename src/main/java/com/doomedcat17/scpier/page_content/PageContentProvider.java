package com.doomedcat17.scpier.page_content;

import com.doomedcat17.scpier.exceptions.ScpObjectNotFoundException;
import com.doomedcat17.scpier.page_content.html.document.*;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class PageContentProvider {

    public PageContent getPageContent(String url) throws ScpObjectNotFoundException {
        HTMLDocumentProvider htmlDocumentProvider = new HTMLDocumentProvider();
        HTMLDocumentInterpreter htmlDocumentInterpreter =
                new HTMLDocumentInterpreter(new DocumentContentCleanerImpl(),
                    new HTMLRedirectionHandler(htmlDocumentProvider),
                    new PageTagsScrapperImpl()
                );
        try {
            Document pageDocument = htmlDocumentProvider.getWebpageContent(url);
            PageContent pageContent = new PageContent();
            pageContent.setSourceUrl(url);
            htmlDocumentInterpreter.mapDocument(pageDocument, pageContent);
            return pageContent;
        } catch (IOException e) {
            throw new ScpObjectNotFoundException(url);
        }
    }

}
