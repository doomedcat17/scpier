package com.doomedcat17.scpier.scp.scp_mappers;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.scp_object.ScpObject;
import com.doomedcat17.scpier.page_content.PageContent;

public interface ScpMapper {

    ScpObject mapToScp(PageContent pageContent, TitleResolver titleResolver);
}
