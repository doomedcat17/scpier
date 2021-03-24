package com.doomedcat17.scpier.mapper.tale_mappers;

import com.doomedcat17.scpier.data.tale.ScpTale;
import com.doomedcat17.scpier.page_content.PageContent;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;

public interface ScpTaleMapper {

    ScpTale mapToTale(PageContent pageContent, TitleResolver titleResolver);
}
