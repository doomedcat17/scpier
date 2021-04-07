package com.doomedcat17.scpier.mapper.tale;

import com.doomedcat17.scpier.data.scp.ScpTale;
import com.doomedcat17.scpier.pagecontent.PageContent;

public interface ScpTaleMapper {

    ScpTale mapToTale(PageContent pageContent);
}
