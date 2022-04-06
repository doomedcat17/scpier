package com.doomedcat17.scpier.page.html.document.provider.iframe.mapper;

import com.doomedcat17.scpier.page.WikiContent;
import org.jsoup.nodes.Element;

import java.io.IOException;

public interface IframeMapper {

    Element map(Element iframeElement, WikiContent wikiContent) throws IOException;
}
