package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.SCPWikiContentNotFound;
import com.doomedcat17.scpier.exception.SCPierApiException;
import com.doomedcat17.scpier.exception.SCPierApiInternalException;
import com.doomedcat17.scpier.mapper.scp.ScpMapperProvider;
import com.doomedcat17.scpier.mapper.scp.ScpWikiContentMapper;
import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.PageContentProvider;

public class ScpFoundationDataProvider {

    private final PageContentProvider pageContentProvider = new PageContentProvider();

    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch) throws SCPierApiException {
        return getScpWikiData(articleName, scpBranch, SCPTranslation.ORIGINAL);
    }

    public ScpWikiData getScpWikiData(String articleName, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPierApiException {
        try {
        PageContent pageContent = getPageContent(articleName, scpBranch, scpTranslation);
        ScpWikiContentMapper scpWikiContentMapper = ScpMapperProvider.getScpMapper(pageContent.getName());
        ScpWikiData scpWikiData = scpWikiContentMapper.mapToScp(pageContent);
        scpWikiData.setTags(pageContent.getTags());
            return scpWikiData;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new SCPierApiInternalException(articleName, scpBranch, scpTranslation);
        }
    }

    private PageContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound {
        PageContent pageContent = pageContentProvider.getPageContent(name, scpBranch, scpTranslation);
        if (scpTranslation.equals(SCPTranslation.ORIGINAL)) {
            pageContent.setTranslationIdentifier(scpBranch.identifier);
        } else pageContent.setTranslationIdentifier(scpTranslation.identifier);
        return pageContent;
    }
}
