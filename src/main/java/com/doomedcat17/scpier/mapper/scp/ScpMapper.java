package com.doomedcat17.scpier.mapper.scp;

import com.doomedcat17.scpier.data.scp.ScpObject;
import com.doomedcat17.scpier.pagecontent.PageContent;

public interface ScpMapper {

    ScpObject mapToScp(PageContent pageContent);
}
