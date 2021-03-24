package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.scp_object.SCPBranch;
import com.doomedcat17.scpier.data.scp_object.SCPTranslation;
import com.doomedcat17.scpier.data.tale.ScpTale;
import com.doomedcat17.scpier.exceptions.ScpObjectNotFoundException;
import com.doomedcat17.scpier.exceptions.ScpTaleNotFoundException;
import com.doomedcat17.scpier.mapper.tale_mappers.ScpTaleMapper;
import com.doomedcat17.scpier.mapper.tale_mappers.ScpTaleMapperProvider;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolverProvider;
import com.doomedcat17.scpier.page_content.PageContent;
import com.doomedcat17.scpier.page_content.PageContentProvider;
import com.doomedcat17.scpier.mapper.SourceBuilder;
import com.doomedcat17.scpier.mapper.scp_mappers.ScpMapper;
import com.doomedcat17.scpier.mapper.scp_mappers.ScpMapperProvider;
import com.doomedcat17.scpier.data.scp_object.ScpObject;

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
            TitleResolver titleResolver =
                    TitleResolverProvider.getTitleResolver(pageContent.getLangIdentifier());
            ScpMapper scpMapper = ScpMapperProvider.getScpMapper(pageContent.getName());
            ScpObject scpObject = scpMapper.mapToScp(pageContent, titleResolver);
            scpObject.setTags(pageContent.getTags());
            return scpObject;
        } catch (Exception e) {
            throw new ScpObjectNotFoundException(e.getMessage());
        }
    }

    public ScpTale getScpTale(String taleTittle, SCPBranch scpBranch, SCPTranslation scpTranslation) throws ScpTaleNotFoundException {
        try {
            PageContent pageContent = getPageContent(taleTittle, scpBranch, scpTranslation);
            TitleResolver titleResolver =
                    TitleResolverProvider.getTitleResolver(pageContent.getLangIdentifier());
            ScpTaleMapper scpTaleMapper = ScpTaleMapperProvider.getScpTaleMapper(taleTittle);
            ScpTale scpTale = scpTaleMapper.mapToTale(pageContent, titleResolver);
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
