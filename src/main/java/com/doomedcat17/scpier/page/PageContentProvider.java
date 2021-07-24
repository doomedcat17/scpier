package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.exception.SCPWikiContentNotFound;
import com.doomedcat17.scpier.page.html.document.cleaner.HTMLDocumentContentCleanerImpl;
import com.doomedcat17.scpier.page.html.document.interpreter.HTMLDocumentInterpreter;
import com.doomedcat17.scpier.page.html.document.provider.DefaultHTMLDocumentProvider;
import com.doomedcat17.scpier.page.html.document.provider.HTMLDocumentProvider;
import com.doomedcat17.scpier.page.html.document.redirection.HTMLRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapperImpl;

import java.io.IOException;

public class PageContentProvider {

    public PageContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound {
        try {
            HTMLDocumentProvider htmlDocumentProvider = new DefaultHTMLDocumentProvider();
            HTMLDocumentInterpreter htmlDocumentInterpreter =
                    new HTMLDocumentInterpreter(new HTMLDocumentContentCleanerImpl(),
                            new HTMLRedirectionHandler(htmlDocumentProvider),
                            new PageTagsScrapperImpl()
                    );
            String url = SourceBuilder.buildSource(name, scpBranch, scpTranslation);
            PageContent pageContent = htmlDocumentProvider.getWebpageContent(url);
            pageContent.setLangIdentifier(scpBranch.identifier);
            pageContent.setTranslationIdentifier(scpTranslation.identifier);
            htmlDocumentInterpreter.mapContent(pageContent);
            return pageContent;
        } catch (IOException e) {
            throw new SCPWikiContentNotFound(e.getMessage());
        }
    }
}
