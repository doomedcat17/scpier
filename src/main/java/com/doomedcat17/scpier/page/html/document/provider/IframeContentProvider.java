package com.doomedcat17.scpier.page.html.document.provider;

import com.doomedcat17.scpier.exception.page.html.document.provider.IframeContentProviderException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.cleaner.WikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.executor.PresetExecutor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class IframeContentProvider {


    private final WikiContentCleaner wikiContentCleaner;

    private final ScriptedWikiPageProvider scriptedWikiPageProvider;

    private final PresetExecutor presetExecutor;

    public void provideIframesContent(WikiContent wikiContent, Preset preset) throws IframeContentProviderException {
        try {
            Elements iframes = wikiContent.getContent().getElementsByTag("iframe");
            iframes.forEach(element -> replaceWithIframeContent(element, wikiContent.getContentSource(), wikiContent.getName(), wikiContent.getLangIdentifier(), preset));
        } catch (Exception e) {
            throw new IframeContentProviderException(e);
        }
    }

    private void replaceWithIframeContent(Element iframe, String pageSource, String title, String langIdentifier, Preset preset) {
        String source = iframe.attr("src");
        if (isTrash(source)) {
            iframe.remove();
        } else {
            Element iframeContent = new Element("div");
            if (source.contains("youtube") || source.contains("vimeo")) {
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
                    if (preset.getArticleName() != null) {
                        try {
                            webpageContent = presetExecutor.execute(preset, source);
                        } catch (NullPointerException e) {
                            // preset DOES NOT apply to all iframe elements, so default content is provided
                            webpageContent = scriptedWikiPageProvider.getWebpageContent(source);
                        }
                    } else webpageContent = scriptedWikiPageProvider.getWebpageContent(source);
                    if (webpageContent.getContent().children().isEmpty()) {
                        iframe.remove();
                    } else {
                        provideIframesContent(webpageContent, preset);
                        iframeContent = webpageContent.getContent();
                        wikiContentCleaner.removeTrashNodes(iframeContent);
                        replaceIframeWithItsContent(iframe, iframeContent);
                    }
                } catch (Exception e) {
                    iframe.remove();
                }
            }
        }
    }

    private boolean isTrash(String src) {
        return src.contains("/common--javascript/resize-iframe.html");
    }

    private void provideYtVideo(Element iframeContent, String source) {
        StringBuilder src = new StringBuilder(source);
        if (source.startsWith("/")) {
            while(src.charAt(0) == '/') src.deleteCharAt(0);
        }
        Element videoElement = new Element("video");
        Element sourceElement = new Element("source");
        sourceElement.attr("src", src.toString());
        videoElement.appendChild(sourceElement);
        iframeContent.appendChild(videoElement);

    }

    private void replaceIframeWithItsContent(Element iframe, Element iframeContent) {
        if (iframe.parent().childrenSize() == 1) {
            placeNodesBehind(iframe.parent(), iframeContent.childNodes());
            iframe.parent().remove();
        } else {
            placeNodesBehind(iframe, iframeContent.childNodes());
            iframe.remove();
        }
    }

    private void placeNodesBehind(Element mainElement, List<Node> nodesToPlace) {
        nodesToPlace = new ArrayList<>(nodesToPlace);
        Node lastNode = null;
        for (Node node: nodesToPlace) {
            if (lastNode != null) {
                lastNode.after(node);
            } else {
                mainElement.after(node);
            }
            lastNode = node;
        }
    }


    public IframeContentProvider(WikiContentCleaner wikiContentCleaner, ScriptedWikiPageProvider scriptedWikiPageProvider, PresetExecutor presetExecutor) {
        this.wikiContentCleaner = wikiContentCleaner;
        this.scriptedWikiPageProvider = scriptedWikiPageProvider;
        this.presetExecutor = presetExecutor;
    }
}
