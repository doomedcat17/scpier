package com.doomedcat17.scpier.page.html.document.provider.iframe.mapper;

import com.doomedcat17.scpier.page.html.document.preset.executor.PresetExecutor;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import org.jsoup.nodes.Element;

public class IframeMapperProvider {

    public static IframeMapper getMapper(Element iframeElement, WikiPageProvider wikiPageProvider, PresetExecutor presetExecutor) {
        if (iframeElement.attr("src").contains("soundcloud")) {
            return new SoundcloudIframeMapper(wikiPageProvider);
        } else if (iframeElement.attr("src").contains("youtube") || iframeElement.attr("src").contains("vimeo")) {
            return new VideoIframeMapper();
        } else {
            return new DefaultIframeMapper(wikiPageProvider, presetExecutor);
        }
    }
}
