package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.exception.SCPWikiContentNotFound;
import com.doomedcat17.scpier.exception.WikiPresetNotFound;
import com.doomedcat17.scpier.page.html.document.cleaner.DefaultWikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.interpreter.WikiPageInterpreter;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.PresetProvider;
import com.doomedcat17.scpier.page.html.document.provider.DefaultWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.doomedcat17.scpier.page.html.document.redirection.WikiRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapperImpl;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WikiContentProvider {

    private final PresetProvider presetProvider;

    public WikiContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) throws SCPWikiContentNotFound {
        try {
            WikiPageProvider wikiPageProvider = new DefaultWikiPageProvider();
            WikiPageInterpreter wikiPageInterpreter =
                    new WikiPageInterpreter(new DefaultWikiContentCleaner(ResourcesProvider.getRemovalDefinitions()),
                            new WikiRedirectionHandler(wikiPageProvider, ResourcesProvider.getRedirectionDefinitions()),
                            new PageTagsScrapperImpl()
                    );
            String url = WikiSourceBuilder.buildSource(name, scpBranch, scpTranslation);
            WikiContent wikiContent = wikiPageProvider.getWebpageContent(url);
            getOffsets(wikiContent, url, wikiPageProvider).forEach(node -> wikiContent.getContent().selectFirst("#page-content").appendChild(node));
            wikiContent.setLangIdentifier(scpBranch.identifier);
            wikiContent.setTranslationIdentifier(scpTranslation.identifier);
            Preset preset;
            try {
                preset = presetProvider.getPresetByNameAndBranch(name, scpBranch);
                wikiContent.setPreset(preset);
            } catch (WikiPresetNotFound e) {
                preset = new Preset();
            }
            wikiPageInterpreter.mapContent(wikiContent);
            if (!preset.getOuterContentNames().isEmpty()) {
                getOuterContent(preset.getOuterContentNames(), scpBranch, scpTranslation)
                        .forEach(node -> wikiContent.getContent().appendChild(node));
            }
            return wikiContent;
        } catch (IOException e) {
            throw new SCPWikiContentNotFound(e.getMessage());
        }
    }


    private List<Node> getOuterContent(List<String> outerContentNames, SCPBranch branch, SCPTranslation translation) {
        List<Node> outerContent = new ArrayList<>();
        for(String outerContentName: outerContentNames) {
            try {
                WikiContent wikiContent = getPageContent(outerContentName, branch, translation);
                outerContent.addAll(wikiContent.getContent().childNodes());
            } catch (SCPWikiContentNotFound ignored) {
            }
        }
        return outerContent;
    }

    private List<Node> getOffsets(WikiContent originalContent, String url, WikiPageProvider wikiPageProvider) {
        List<Node> offsetsContent = new ArrayList<>();
        Element previousContent = originalContent.getContent().selectFirst("#page-content");
        for (int i = 1; i <= 5; i++) {
            try {
                WikiContent wikiContent = wikiPageProvider.getWebpageContent(url+"/offset/"+i);
                Element content = wikiContent.getContent().selectFirst("#page-content");
                if (previousContent.html().equals(content.html())) break;
                offsetsContent.addAll(content.childNodes());
                previousContent = content;
            } catch (IOException e) {
                break;
            }
        }
        return offsetsContent;
    }

    public WikiContentProvider() {
        this.presetProvider = ResourcesProvider.getPresetProvider();
    }
}
