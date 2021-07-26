package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.exception.SCPWikiContentNotFound;
import com.doomedcat17.scpier.page.html.document.cleaner.DefaultWikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.interpreter.WikiPageInterpreter;
import com.doomedcat17.scpier.page.html.document.provider.DefaultWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.doomedcat17.scpier.page.html.document.redirection.WikiRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapperImpl;

import java.io.IOException;

public class WikiContentProvider {

    public WikiContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound {
        try {
            WikiPageProvider wikiPageProvider = new DefaultWikiPageProvider();
            WikiPageInterpreter wikiPageInterpreter =
                    new WikiPageInterpreter(new DefaultWikiContentCleaner(),
                            new WikiRedirectionHandler(wikiPageProvider),
                            new PageTagsScrapperImpl()
                    );
            String url = WikiSourceBuilder.buildSource(name, scpBranch, scpTranslation);
            WikiContent wikiContent = wikiPageProvider.getWebpageContent(url);
            wikiContent.setLangIdentifier(scpBranch.identifier);
            wikiContent.setTranslationIdentifier(scpTranslation.identifier);
            wikiPageInterpreter.mapContent(wikiContent);
            return wikiContent;
        } catch (IOException e) {
            throw new SCPWikiContentNotFound(e.getMessage());
        }
    }
}
