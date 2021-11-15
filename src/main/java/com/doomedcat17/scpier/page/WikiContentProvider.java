package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFound;
import com.doomedcat17.scpier.exception.page.html.document.preset.WikiPresetNotFoundException;
import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import com.doomedcat17.scpier.page.html.document.author.AuthorScraper;
import com.doomedcat17.scpier.page.html.document.cleaner.DefaultWikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.interpreter.WikiPageInterpreter;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.PresetProvider;
import com.doomedcat17.scpier.page.html.document.provider.DefaultWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.offset.OffsetsProvider;
import com.doomedcat17.scpier.page.html.document.redirection.WikiRedirectionHandler;
import com.doomedcat17.scpier.page.html.document.revision.LastRevisionTimestampProvider;
import com.doomedcat17.scpier.page.html.document.tags.PageTagsScrapperImpl;
import com.gargoylesoftware.htmlunit.WebClient;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WikiContentProvider {

    private final PresetProvider presetProvider;

    public WikiContent getPageContent(String name, SCPBranch scpBranch, SCPTranslation scpTranslation, WebClient webClient) throws SCPWikiContentNotFound, RevisionDateException {
        try {
            WikiPageProvider wikiPageProvider = new DefaultWikiPageProvider();
            WikiPageInterpreter wikiPageInterpreter =
                    new WikiPageInterpreter(webClient);
            String url = WikiSourceBuilder.buildSource(name.toLowerCase(), scpBranch, scpTranslation);
            name = url.substring(url.lastIndexOf('/') + 1);
            WikiContent wikiContent = wikiPageProvider.getWebpageContent(url);
            if (scpTranslation != SCPTranslation.ORIGINAL) {
                wikiContent.setTranslationSourceUrl(url);
                wikiContent.setOriginalSourceUrl(
                        WikiSourceBuilder.buildSource(name.toLowerCase(), scpBranch, SCPTranslation.ORIGINAL)
                );
                //
                if (wikiContent.getOriginalSourceUrl().equals(wikiContent.getTranslationSourceUrl())) {
                    wikiContent.setTranslationSourceUrl("");
                }
            }
            wikiContent.setLastRevisionTimestamp(
                    LastRevisionTimestampProvider.getLastRevisionTimestamp(wikiContent.getContent())
            );
            if (wikiContent.getContent().selectFirst("#page-content") == null) throw new IOException();
            wikiContent.setName(name);
            OffsetsProvider.getOffsetsContent(wikiContent, wikiPageProvider)
                    .forEach(node ->
                            wikiContent.getContent()
                                    .selectFirst("#page-content")
                                    .appendChild(node));
            wikiContent.setLangIdentifier(scpBranch.identifier);
            wikiContent.setTranslationIdentifier(scpTranslation.identifier);
            Preset preset;
            try {
                preset = presetProvider.getPresetByNameAndBranch(name, scpBranch);
                wikiContent.setPreset(preset);
            } catch (WikiPresetNotFoundException e) {
                preset = new Preset();
            }
            wikiPageInterpreter.mapContent(wikiContent);
            if (!preset.getOuterContentNames().isEmpty()) {
                getOuterContent(wikiContent, preset, scpBranch, scpTranslation, webClient);
            }
            return wikiContent;
        } catch (IOException e) {
            throw new SCPWikiContentNotFound("Wiki content has not been found: " + name + ", " + scpBranch + ", " + scpTranslation, e);
        }
    }


    private void getOuterContent(WikiContent wikiContent, Preset preset, SCPBranch branch, SCPTranslation translation, WebClient webClient) {
        List<Node> outerContent = new ArrayList<>();
        for (String outerContentName : preset.getOuterContentNames()) {
            try {
                WikiContent outerWikiContent = getPageContent(outerContentName, branch, translation, webClient);
                outerContent.addAll(outerWikiContent.getContent().childNodes());
                if (outerWikiContent.getLastRevisionTimestamp().after(wikiContent.getLastRevisionTimestamp())) {
                    wikiContent.setLastRevisionTimestamp(outerWikiContent.getLastRevisionTimestamp());
                }
            } catch (SCPWikiContentNotFound | RevisionDateException ignored) {
            }
        }
        outerContent.forEach(node -> wikiContent.getContent().appendChild(node));
    }

    public WikiContentProvider() {
        this.presetProvider = ResourcesProvider.getPresetProvider();
    }
}
