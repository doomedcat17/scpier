package com.doomedcat17.scpier.mapper.tale;

import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.page.PageContent;

public interface ScpTaleMapper {

    ScpWikiData mapToTale(PageContent pageContent);
}
