package com.doomedcat17.scpier.page.html.document.provider.iframe;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.exception.page.html.document.provider.IframeContentProviderException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.cleaner.WikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.preset.executor.PresetExecutor;
import com.doomedcat17.scpier.page.html.document.provider.DefaultWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.iframe.mapper.IframeMapper;
import com.doomedcat17.scpier.page.html.document.provider.iframe.mapper.IframeMapperProvider;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class IframeContentProvider {

    private final WikiContentCleaner wikiContentCleaner;
    private final DefaultWikiPageProvider defaultWikiPageProvider;
    private final PresetExecutor presetExecutor;

    public IframeContentProvider(WikiContentCleaner wikiContentCleaner, DefaultWikiPageProvider defaultWikiPageProvider, PresetExecutor presetExecutor) {
        this.wikiContentCleaner = wikiContentCleaner;
        this.defaultWikiPageProvider = defaultWikiPageProvider;
        this.presetExecutor = presetExecutor;
    }

    public void includeIframesContent(WikiContent wikiContent) throws IframeContentProviderException {
        try {
            Elements iframes = wikiContent.getContent().select("iframe");
            iframes.forEach(element -> replaceWithIframeContent(element, wikiContent));
        } catch (Exception e) {
            throw new IframeContentProviderException(e);
        }
    }

    private void replaceWithIframeContent(Element iframe, WikiContent wikiContent) {
        if (isTrash(iframe)) {
            iframe.remove();
        } else {
            try {
            IframeMapper iframeMapper =
                    IframeMapperProvider.getMapper(iframe, defaultWikiPageProvider, presetExecutor);
            Element iframeContent = iframeMapper.map(iframe, wikiContent);
            if (Objects.isNull(iframeContent) || iframeContent.children().isEmpty()) {
                iframe.remove();
            } else {
                WikiContent iframeWikiContent = new WikiContent(wikiContent);
                iframeWikiContent.setContent(iframeContent);
                includeIframesContent(iframeWikiContent);
                wikiContentCleaner.removeTrashNodes(iframeContent);
                replaceIframeWithItsContent(iframe, iframeContent);
            }
            } catch (Exception e) {
                iframe.remove();
            }
        }
    }

    private boolean isTrash(Element iframeElement) {
        Set<String> definitions = ResourcesProvider.getTrashIframesDefinitions();
        return definitions.stream().anyMatch(iframeElement::is);
    }


    private void replaceIframeWithItsContent(Element iframe, Element iframeContent) {
        if (iframe.parent().childrenSize() == 1) {
            placeAfterElement(iframe.parent(), iframeContent.children());
            iframe.parent().remove();
        } else {
            placeAfterElement(iframe, iframeContent.children());
            iframe.remove();
        }
    }

    private void placeAfterElement(Element elementToPlaceAfter, List<Element> elements) {
        Element lastElement = elementToPlaceAfter;
        for (Element child: elements) {
            lastElement.after(child);
            lastElement = child;
        }
    }

}
