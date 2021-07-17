package com.doomedcat17.scpier.mapper.scp;

import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.ScpMapperException;
import com.doomedcat17.scpier.pagecontent.PageContent;

public interface ScpMapper {

    ScpWikiData mapToScp(PageContent pageContent) throws ScpMapperException;
}
