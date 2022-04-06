package com.doomedcat17.scpier.page.html.document.provider.iframe.mapper;

import com.doomedcat17.scpier.page.WikiContent;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class VideoIframeMapper implements  IframeMapper{
    @Override
    public Element map(Element iframeElement, WikiContent wikiContent) throws IOException {
        Element content = new Element("div");
        String source = iframeElement.attr("src");
        if (source.isEmpty()) return content;
        StringBuilder src = new StringBuilder(source);
        while(src.charAt(0) == '/') src.deleteCharAt(0);
        Element videoElement = new Element("video");
        Element sourceElement = new Element("source");
        sourceElement.attr("src", src.toString());
        videoElement.appendChild(sourceElement);
        content.appendChild(videoElement);
        return content;
    }
}
