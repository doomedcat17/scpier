package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.exception.SCPWikiContentNotFound;
import com.doomedcat17.scpier.page.html.document.*;

import java.io.IOException;

public class PageContentProvider {

    public PageContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound {
        try {
            HTMLDocumentProvider htmlDocumentProvider = HTMLDocumentProviderFactory.getHTMLDocumentProvider(name, scpBranch, scpTranslation);
            HTMLDocumentInterpreter htmlDocumentInterpreter =
                    new HTMLDocumentInterpreter(new HTMLDocumentContentCleanerImpl(),
                            new HTMLRedirectionHandler(htmlDocumentProvider),
                            new PageTagsScrapperImpl()
                    );
            String url = SourceBuilder.buildSource(name, scpBranch, scpTranslation);
            PageContent pageContent = htmlDocumentProvider.getWebpageContent(url);
            if (scpTranslation.equals(SCPTranslation.ORIGINAL)) {
                pageContent.setLangIdentifier(scpBranch.identifier);
            } else pageContent.setLangIdentifier(scpTranslation.identifier);
            htmlDocumentInterpreter.mapContent(pageContent);
            return pageContent;
        } catch (IOException e) {
            throw new SCPWikiContentNotFound(e.getMessage());
        }
    }
}
