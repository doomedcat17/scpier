package com.doomedcat17.scpier.mapper.scp_mappers;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.page_content.PageContent;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.mapper.scp_mappers.appendix_mappers.AppendixMapper;
import org.jsoup.nodes.Node;
import com.doomedcat17.scpier.data.scp_object.ScpObject;

import java.util.ArrayList;
import java.util.List;

public class DefaultScpMapper implements ScpMapper {
    public ScpObject mapToScp(PageContent pageContent, TitleResolver titleResolver) {
        List<Node> data = pageContent.getContent().childNodes();
        ScpObject scpObject = new ScpObject();
        scpObject.setObjectName(pageContent.getName());
        scpObject.setSource(pageContent.getSourceUrl());
        mapScp(scpObject, data, titleResolver);
        ScpAppendicesResolver.appendicesCleanup(scpObject, titleResolver);
        return scpObject;
    }

    private void mapScp(ScpObject scpObject, List<Node> nodes, TitleResolver titleResolver) {
        List<Appendix> appendices =
                AppendixMapper.mapNodesToAppendices(new ArrayList<>(nodes), scpObject.getSource(), titleResolver);
        ScpAppendicesResolver.resolveAppendices(scpObject, appendices);
    }


}
