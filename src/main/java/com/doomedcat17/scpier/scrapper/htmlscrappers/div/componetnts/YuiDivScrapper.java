package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.data.scp_object.MappedScpObject;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.scp_object.ScpObject;
import com.doomedcat17.scpier.mapper.scp_mappers.appendix_mappers.AppendixMapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;

import java.util.ArrayList;
import java.util.List;

public class YuiDivScrapper extends DivScrapper implements DivScrapperComponent {
    public YuiDivScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        List<Appendix> appendices = new ArrayList<>();
        if (element.hasClass("yui-navset")) {
            Element yuiContent = element.selectFirst(".yui-content");
            if (yuiContent != null) appendices.addAll(scrapDivContent(yuiContent));
        } else if (element.hasClass("yui-content")) {
            element.children().stream()
                    .filter(wikiTab -> wikiTab.hasAttr("id"))
                    .filter(wikiTab -> wikiTab.attr("id").contains("wiki-tab-"))
                    .map(this::mapWikiTab)
                    .forEach(appendices::addAll);
        } else if (element.attr("id").contains("wiki-tab-")) appendices.addAll(mapWikiTab(element));
        return appendices;
    }

    private List<Appendix> mapWikiTab(Element wikiTab) {
        MappedScpObject scpObject = new MappedScpObject();
        wikiTab.attr("id", "page-content");
        AppendixMapper.mapNodesToAppendices(wikiTab.childNodes(), source, titleResolver);
        return scpObject.getContent();
    }
}
