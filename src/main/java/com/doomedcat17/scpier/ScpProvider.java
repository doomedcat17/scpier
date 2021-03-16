package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.scp_object.SCPBranch;
import com.doomedcat17.scpier.data.scp_object.SCPTranslation;
import com.doomedcat17.scpier.exceptions.ScpObjectNotFoundException;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolverProvider;
import com.doomedcat17.scpier.page_content.PageContent;
import com.doomedcat17.scpier.page_content.PageContentProvider;
import com.doomedcat17.scpier.scp.ScpSourceBuilder;
import com.doomedcat17.scpier.scp.scp_mappers.ScpMapper;
import com.doomedcat17.scpier.scp.scp_mappers.ScpMapperProvider;
import com.doomedcat17.scpier.data.scp_object.ScpObject;

import java.io.IOException;

public class ScpProvider {

    private PageContentProvider pageContentProvider = new PageContentProvider();

    private final ScpSourceBuilder scpSourceBuilder = new ScpSourceBuilder();

    public ScpObject getScpObject(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws ScpObjectNotFoundException {
        String source = scpSourceBuilder.buildScpSource(name, scpBranch, scpTranslation);
        PageContent pageContent = pageContentProvider.getPageContent(source);
        if (scpTranslation.equals(SCPTranslation.ORIGINAL)) {
            pageContent.setLangIdentifier(scpBranch.identifier);
        } else pageContent.setLangIdentifier(scpTranslation.identifier);
        TitleResolver titleResolver =
                TitleResolverProvider.getTitleResolver(pageContent.getLangIdentifier());
        ScpMapper scpMapper = ScpMapperProvider.getScpMapper(pageContent.getScpName());
        ScpObject scpObject = scpMapper.mapToScp(pageContent, titleResolver);
        scpObject.setTags(pageContent.getTags());
        return scpObject;
    }
}
