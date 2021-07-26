package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.SCPWikiContentNotFound;
import com.doomedcat17.scpier.exception.SCPierApiException;
import com.doomedcat17.scpier.exception.SCPierApiInternalException;
import com.doomedcat17.scpier.mapper.scp.ScpMapperProvider;
import com.doomedcat17.scpier.mapper.scp.ScpWikiContentMapper;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.WikiContentProvider;

public class ScpFoundationDataProvider {

    private final WikiContentProvider wikiContentProvider = new WikiContentProvider();

    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch) throws SCPierApiException {
        return getScpWikiData(articleName, scpBranch, SCPTranslation.ORIGINAL);
    }

    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPierApiException {
        try {
        WikiContent wikiContent = getPageContent(articleName, scpBranch, scpTranslation);
        ScpWikiContentMapper scpWikiContentMapper = ScpMapperProvider.getScpMapper(wikiContent.getName());
        ScpWikiData scpWikiData = scpWikiContentMapper.mapToScp(wikiContent);
        scpWikiData.setTags(wikiContent.getTags());
            return scpWikiData;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new SCPierApiInternalException(articleName, scpBranch, scpTranslation);
        }
    }

    private WikiContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound {
        WikiContent wikiContent = wikiContentProvider.getPageContent(name, scpBranch, scpTranslation);
        if (scpTranslation.equals(SCPTranslation.ORIGINAL)) {
            wikiContent.setTranslationIdentifier(scpBranch.identifier);
        } else wikiContent.setTranslationIdentifier(scpTranslation.identifier);
        return wikiContent;
    }
}
