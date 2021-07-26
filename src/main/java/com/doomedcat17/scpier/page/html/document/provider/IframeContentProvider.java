package com.doomedcat17.scpier.page.html.document.provider;

import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.cleaner.WikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.executor.PresetExecutor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class IframeContentProvider {

    private final ScriptedWikiPageProvider scriptedHTMLDocumentProvider;

    private final WikiContentCleaner wikiContentCleaner;

    public void provideIframesContent(WikiContent wikiContent, Preset preset) {
        Elements iframes = wikiContent.getContent().getElementsByTag("iframe");
        iframes.forEach(element -> replaceWithIframeContent(element, wikiContent.getSourceUrl(), wikiContent.getName(), wikiContent.getLangIdentifier(), preset));
    }

    private void replaceWithIframeContent(Element iframe, String pageSource, String title, String langIdentifier, Preset preset) {
        String source = iframe.attr("src");
        Element iframeContent = new Element("div");
        if (source.contains("youtube")) {
            provideYtVideo(iframeContent, source);
            if (iframe.parent().childrenSize() == 1) {
                iframeContent.children().forEach(element -> iframe.parent().after(element));
                iframe.parent().remove();
            } else {
                iframeContent.children().forEach(iframe::after);
                iframe.remove();
            }
        } else {
            if (source.startsWith("/")) {
                source = pageSource.substring(0, pageSource.lastIndexOf('/')) + source;
            }
            try {
                WikiContent webpageContent;
                if (preset.getName() != null) {
                    try {
                        webpageContent = PresetExecutor.execute(preset, source);
                    } catch (NullPointerException e) {
                        // preset DOES NOT apply to all iframe elements, so default content is provided
                        webpageContent = scriptedHTMLDocumentProvider.getWebpageContent(source);
                    }
                } else webpageContent = scriptedHTMLDocumentProvider.getWebpageContent(source);
                if (webpageContent.getContent().children().isEmpty()) {
                    iframe.remove();
                } else {
                    webpageContent.setName(title);
                    webpageContent.setLangIdentifier(langIdentifier);
                    webpageContent.setSourceUrl(source);
                    provideIframesContent(webpageContent, preset);
                    iframeContent = webpageContent.getContent();
                    wikiContentCleaner.removeTrash(iframeContent);
                    replaceIframeWithItsContent(iframe, iframeContent);
                }
            } catch (Exception ignored) {
            }
        }
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

    private void replaceIframeWithItsContent(Element iframe, Element iframeContent) {
        if (iframe.parent().childrenSize() == 1) {
            placeElementsBehind(iframe.parent(), iframeContent.children());
            iframe.parent().remove();
        } else {
            placeElementsBehind(iframe, iframeContent.children());
            iframe.remove();
        }
    }

    private void placeElementsBehind(Element mainElement, List<Element> elementsToPlace) {
        Element lastElement = null;
        for (Element element: elementsToPlace) {
            if (lastElement != null) {
                lastElement.after(element);
            } else {
                mainElement.after(element);
                lastElement = element;
            }
        }
    }



    public IframeContentProvider(ScriptedWikiPageProvider scriptedHTMLDocumentProvider, WikiContentCleaner wikiContentCleaner) {
        this.scriptedHTMLDocumentProvider = scriptedHTMLDocumentProvider;
        this.wikiContentCleaner = wikiContentCleaner;
    }
}
