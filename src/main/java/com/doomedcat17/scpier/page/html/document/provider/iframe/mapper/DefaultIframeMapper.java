package com.doomedcat17.scpier.page.html.document.provider.iframe.mapper;

import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.executor.PresetExecutor;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Objects;

public class DefaultIframeMapper implements IframeMapper{

    private final WikiPageProvider wikiPageProvider;
    private final PresetExecutor presetExecutor;

    public DefaultIframeMapper(WikiPageProvider wikiPageProvider, PresetExecutor presetExecutor) {
        this.wikiPageProvider = wikiPageProvider;
        this.presetExecutor = presetExecutor;
    }

    @Override
    public Element map(Element iframeElement, WikiContent wikiContent) throws IOException {
        String source = iframeElement.attr("src");
        Preset preset = wikiContent.getPreset();
        if (source.isEmpty()) return new Element("div");
        if (source.startsWith("/")) {
            source = wikiContent.getContentSource().substring(0, wikiContent.getContentSource().lastIndexOf('/')) + source;
        }
            WikiContent webpageContent;
            if (Objects.nonNull(preset)) {
                    webpageContent = presetExecutor.execute(preset, source);
            } else webpageContent = wikiPageProvider.getWebpageContent(source);
            return webpageContent.getContent();
    }
}
