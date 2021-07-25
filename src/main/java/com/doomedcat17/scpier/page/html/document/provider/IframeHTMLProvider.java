package com.doomedcat17.scpier.page.html.document.provider;

import com.doomedcat17.scpier.exception.WikiPresetNotFound;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.cleaner.WikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.preset.PresetWikiContentProvider;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IframeHTMLProvider {

    private final PresetWikiContentProvider presetWikiContentProvider;

    private final ScriptedWikiPageProvider scriptedHTMLDocumentProvider;

    private final WikiContentCleaner wikiContentCleaner;

    public void provideIframesContent(WikiContent wikiContent) {
        Elements iframes = wikiContent.getContent().getElementsByTag("iframe");
        iframes.forEach(element -> replaceWithIframeContent(element, wikiContent.getSourceUrl(), wikiContent.getName(), wikiContent.getLangIdentifier()));
    }

    private void replaceWithIframeContent(Element iframe, String pageSource, String title, String langIdentifier) {
        String source = iframe.attr("src");
        Element iframeContent = new Element("div");
        if (source.contains("youtube")) {
            provideYtVideo(iframeContent, source);
        } else {
            if (source.startsWith("/")) {
                source = pageSource.substring(0, pageSource.lastIndexOf('/')) + source;
            }
            try {
                WikiContent webpageContent;
                try {
                    webpageContent = presetWikiContentProvider.runJsAndGetContent(title, langIdentifier, source);
                } catch (WikiPresetNotFound e) {
                    webpageContent = scriptedHTMLDocumentProvider.getWebpageContent(source);
                }
                webpageContent.setName(title);
                webpageContent.setLangIdentifier(langIdentifier);
                webpageContent.setSourceUrl(source);
                provideIframesContent(webpageContent);
                iframeContent = webpageContent.getContent();
                wikiContentCleaner.removeTrash(iframeContent);
            } catch (Exception ignored) {
            }
        }
        if (!iframeContent.childNodes().isEmpty()) {
            if (iframe.parent().childrenSize() == 1) {
                iframeContent.children().forEach(element -> iframe.parent().after(element));
                iframe.parent().remove();
            } else {
                iframeContent.children().forEach(iframe::after);
                iframe.remove();
            }
        } else iframe.remove();
    }

    private void provideYtVideo(Element iframeContent, String source) {
        StringBuilder src = new StringBuilder(source);
        if (source.startsWith("/")) {
            while(src.charAt(0) == '/') src.deleteCharAt(0);
        }
        Element videoElement = new Element("video");
        videoElement.addClass("youtube-video");
        Element sourceElement = new Element("source");
        sourceElement.attr("src", src.toString());
        videoElement.appendChild(sourceElement);
        iframeContent.appendChild(videoElement);
    }

    public IframeHTMLProvider(ScriptedWikiPageProvider scriptedHTMLDocumentProvider, WikiContentCleaner wikiContentCleaner) {
        this.presetWikiContentProvider = new PresetWikiContentProvider();
        this.scriptedHTMLDocumentProvider = scriptedHTMLDocumentProvider;
        this.wikiContentCleaner = wikiContentCleaner;
    }
}
