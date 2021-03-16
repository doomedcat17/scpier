package com.doomedcat17.scpier.page_content.html.document;

import com.doomedcat17.scpier.page_content.PageContent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IframeProvider {

    private HTMLDocumentProvider htmlDocumentProvider;

    private DocumentContentCleaner documentContentCleaner;

    public void provideIframesContent(PageContent pageContent) {
        Elements iframes = pageContent.getContent().select(":root iframe");
        iframes.forEach(element -> replaceWithIframeContent(element, pageContent.getSourceUrl(), documentContentCleaner));
    }

    private void replaceWithIframeContent(Element iframe, String pageSource, DocumentContentCleaner documentContentCleaner) {
        String source = iframe.attr("src");
        Element iframeContent = new Element("div");
        if (source.contains("youtube")) {
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
        } else {
            if (source.startsWith("/")) {
                source = pageSource.substring(0, pageSource.lastIndexOf('/')) + source;
            }
            try {
                Document document = htmlDocumentProvider.getWebpageContent(source);
                iframeContent = document.selectFirst("body");
                documentContentCleaner.removeTrash(iframeContent);
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
        }
    }

    public IframeProvider(HTMLDocumentProvider htmlDocumentProvider, DocumentContentCleaner documentContentCleaner) {
        this.htmlDocumentProvider = htmlDocumentProvider;
        this.documentContentCleaner = documentContentCleaner;
    }
}
