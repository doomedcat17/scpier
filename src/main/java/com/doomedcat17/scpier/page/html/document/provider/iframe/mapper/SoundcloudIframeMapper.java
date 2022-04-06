package com.doomedcat17.scpier.page.html.document.provider.iframe.mapper;

import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class SoundcloudIframeMapper implements IframeMapper {

    private final WikiPageProvider wikiPageProvider;
    public SoundcloudIframeMapper(WikiPageProvider wikiPageProvider) {
        this.wikiPageProvider = wikiPageProvider;
    }

    @Override
    public Element map(Element iframeElement, WikiContent wikiContent) throws IOException {
        Element content = new Element("div");
        Element playerElement = wikiPageProvider.getWebpageContent(iframeElement.attr("src")).getContent();
        Element soundCloudLinkElement = playerElement.selectFirst("head link[rel=canonical]");
        String link;
        if (soundCloudLinkElement != null) {
            link = soundCloudLinkElement.attr("href");
        } else return content;
        Element sourceElement = new Element("source");
        sourceElement.attr("src", link);
        Element audioElement = new Element("audio");
        audioElement.appendChild(sourceElement);
        content.appendChild(audioElement);
        return content;
    }
}
