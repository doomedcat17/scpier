package com.doomedcat17.scpier.mapper.tale;

import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.pagecontent.PageContent;

public interface ScpTaleMapper {

    ScpWikiData mapToTale(PageContent pageContent);
}
