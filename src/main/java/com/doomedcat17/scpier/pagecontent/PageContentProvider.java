package com.doomedcat17.scpier.pagecontent;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.pagecontent.html.document.*;

import java.io.IOException;

public class PageContentProvider {

    public PageContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws IOException {
        HTMLDocumentProvider htmlDocumentProvider = HTMLDocumentProviderFactory.getHTMLDocumentProvider(name, scpBranch, scpTranslation);
        HTMLDocumentInterpreter htmlDocumentInterpreter =
                new HTMLDocumentInterpreter(new DocumentContentCleanerImpl(),
                        new HTMLRedirectionHandler(new DefaultHTMLDocumentProvider()),
                        new PageTagsScrapperImpl()
                );
        String url = SourceBuilder.buildSource(name, scpBranch, scpTranslation);
        PageContent pageContent = htmlDocumentProvider.getWebpageContent(url);
        if (scpTranslation.equals(SCPTranslation.ORIGINAL)) {
            pageContent.setLangIdentifier(scpBranch.identifier);
        } else pageContent.setLangIdentifier(scpTranslation.identifier);
        htmlDocumentInterpreter.mapContent(pageContent);
        return pageContent;
    }
}
