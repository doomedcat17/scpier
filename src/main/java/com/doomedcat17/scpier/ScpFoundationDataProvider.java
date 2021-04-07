package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.data.scp.ScpObject;
import com.doomedcat17.scpier.data.scp.ScpTale;
import com.doomedcat17.scpier.exceptions.ScpObjectNotFoundException;
import com.doomedcat17.scpier.exceptions.ScpTaleNotFoundException;
import com.doomedcat17.scpier.mapper.SourceBuilder;
import com.doomedcat17.scpier.mapper.scp.ScpMapper;
import com.doomedcat17.scpier.mapper.scp.ScpMapperProvider;
import com.doomedcat17.scpier.mapper.tale.ScpTaleMapper;
import com.doomedcat17.scpier.mapper.tale.ScpTaleMapperProvider;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.doomedcat17.scpier.pagecontent.PageContentProvider;

import java.io.IOException;

public class ScpFoundationDataProvider {

    private final PageContentProvider pageContentProvider = new PageContentProvider();

    private final SourceBuilder sourceBuilder = new SourceBuilder();

    public ScpObject getScpObject(int objectId, SCPBranch scpBranch, SCPTranslation scpTranslation) throws ScpObjectNotFoundException {
        String name = String.valueOf(objectId);
        return getScpObject(name, scpBranch, scpTranslation);
    }

    public ScpObject getFirstScpObject(String proposalName, SCPBranch scpBranch, SCPTranslation scpTranslation) throws ScpObjectNotFoundException {
        return getScpObject(proposalName, scpBranch, scpTranslation);
    }

    private ScpObject getScpObject(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws ScpObjectNotFoundException {
        try {
            PageContent pageContent = getPageContent(name, scpBranch, scpTranslation);
            ScpMapper scpMapper = ScpMapperProvider.getScpMapper(pageContent.getName());
            ScpObject scpObject = scpMapper.mapToScp(pageContent);
            scpObject.setTags(pageContent.getTags());
            return scpObject;
        } catch (Exception e) {
            throw new ScpObjectNotFoundException(e.getMessage());
        }
    }

    public ScpTale getScpTale(String taleTittle, SCPBranch scpBranch, SCPTranslation scpTranslation) throws ScpTaleNotFoundException {
        try {
            PageContent pageContent = getPageContent(taleTittle, scpBranch, scpTranslation);
            ScpTaleMapper scpTaleMapper = ScpTaleMapperProvider.getScpTaleMapper(taleTittle);
            ScpTale scpTale = scpTaleMapper.mapToTale(pageContent);
            scpTale.setTags(pageContent.getTags());
            return scpTale;
        } catch (Exception e) {
            throw new ScpTaleNotFoundException(e.getMessage());
        }
    }

    private PageContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws IOException {
        String source = sourceBuilder.buildSource(name, scpBranch, scpTranslation);
        PageContent pageContent = pageContentProvider.getPageContent(source);
        if (scpTranslation.equals(SCPTranslation.ORIGINAL)) {
            pageContent.setLangIdentifier(scpBranch.identifier);
        } else pageContent.setLangIdentifier(scpTranslation.identifier);
        return pageContent;
    }
}
