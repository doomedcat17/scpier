package com.doomedcat17.scpier.mapper.scp;

import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.ScpMapperException;
import com.doomedcat17.scpier.page.PageContent;

public interface ScpWikiContentMapper {

    ScpWikiData mapToScp(PageContent pageContent) throws ScpMapperException;
}