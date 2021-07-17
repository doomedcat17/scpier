package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.SCPWikiContentNotFound;
import com.doomedcat17.scpier.exception.ScpierIternalException;
import com.doomedcat17.scpier.mapper.scp.ScpMapper;
import com.doomedcat17.scpier.mapper.scp.ScpMapperProvider;
import com.doomedcat17.scpier.mapper.tale.ScpTaleMapper;
import com.doomedcat17.scpier.mapper.tale.ScpTaleMapperProvider;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.doomedcat17.scpier.pagecontent.PageContentProvider;

public class ScpFoundationDataProvider {

    private final PageContentProvider pageContentProvider = new PageContentProvider();


    public ScpWikiData getScpObject(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound, ScpierIternalException {
        try {
        PageContent pageContent = getPageContent(name, scpBranch, scpTranslation);
        ScpMapper scpMapper = ScpMapperProvider.getScpMapper(pageContent.getName());
        ScpWikiData scpWikiData = scpMapper.mapToScp(pageContent);
        scpWikiData.setTags(pageContent.getTags());
            return scpWikiData;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ScpierIternalException();
        }
    }

    public ScpWikiData getScpTale(String taleTittle, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound, ScpierIternalException {
        try {
            PageContent pageContent = getPageContent(taleTittle, scpBranch, scpTranslation);
            ScpTaleMapper scpTaleMapper = ScpTaleMapperProvider.getScpTaleMapper(taleTittle);
            ScpWikiData scpTale = scpTaleMapper.mapToTale(pageContent);
            scpTale.setTags(pageContent.getTags());
            return scpTale;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ScpierIternalException();
        }
    }

    private PageContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound {
        PageContent pageContent = pageContentProvider.getPageContent(name, scpBranch, scpTranslation);
        if (scpTranslation.equals(SCPTranslation.ORIGINAL)) {
            pageContent.setLangIdentifier(scpBranch.identifier);
        } else pageContent.setLangIdentifier(scpTranslation.identifier);
        return pageContent;
    }
}
