package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.scp.scp_mappers.appendix_mappers.AppendixMapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.List;

public class MaterialBoxScrapper extends DivScrapper implements DivScrapperComponent {
    public MaterialBoxScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        return AppendixMapper.mapNodesToAppendices(element.childNodes(), source, titleResolver);
    }
}
