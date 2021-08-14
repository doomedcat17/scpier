package com.doomedcat17.scpier.mapper.scp;

import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.mapper.ScpMapperException;
import com.doomedcat17.scpier.page.WikiContent;

public interface ScpWikiContentMapper {

    ScpWikiData mapWikiContent(WikiContent wikiContent) throws ScpMapperException;
}
