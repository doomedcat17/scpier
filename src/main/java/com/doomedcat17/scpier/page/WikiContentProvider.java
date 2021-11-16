package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPLanguage;
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFound;
import com.doomedcat17.scpier.exception.page.html.document.preset.WikiPresetNotFoundException;
import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import com.doomedcat17.scpier.page.html.document.interpreter.WikiPageInterpreter;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.PresetProvider;
import com.doomedcat17.scpier.page.html.document.provider.DefaultWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.offset.OffsetsProvider;
import com.doomedcat17.scpier.page.html.document.revision.LastRevisionDateScraper;
import com.gargoylesoftware.htmlunit.WebClient;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WikiContentProvider {

    private final PresetProvider presetProvider;

    public WikiContent getPageContent(String name, SCPBranch scpBranch, SCPLanguage scpLanguage, WebClient webClient) throws SCPWikiContentNotFound, RevisionDateException {
        try {
            WikiPageProvider wikiPageProvider = new DefaultWikiPageProvider();
            WikiPageInterpreter wikiPageInterpreter =
                    new WikiPageInterpreter(webClient, wikiPageProvider);
            String url = WikiSourceBuilder.buildSource(name.toLowerCase(), scpBranch, scpLanguage);
            name = url.substring(url.lastIndexOf('/') + 1);
            WikiContent wikiContent = wikiPageProvider.getWebpageContent(url);
            if (scpLanguage.identifier.equals(scpBranch.identifier)) {
                wikiContent.setTranslationSourceUrl(url);
                wikiContent.setOriginalSourceUrl(
                        WikiSourceBuilder.buildSource(name.toLowerCase(), scpBranch, scpLanguage)
                );
                if (wikiContent.getOriginalSourceUrl().equals(wikiContent.getTranslationSourceUrl())) {
                    wikiContent.setTranslationSourceUrl("");
                }
            }
            LastRevisionDateScraper lastRevisionDateScraper = new LastRevisionDateScraper();
            wikiContent.setLastRevisionTimestamp(
                   lastRevisionDateScraper.scrapDate(wikiContent.getContent())
            );
            if (wikiContent.getContent().selectFirst("#page-content") == null) throw new IOException();
            wikiContent.setName(name);
            OffsetsProvider.getOffsetsContent(wikiContent, wikiPageProvider)
                    .forEach(node ->
                            wikiContent.getContent()
                                    .selectFirst("#page-content")
                                    .appendChild(node));
            Preset preset;
            try {
                preset = presetProvider.getPresetByNameAndBranch(name, scpBranch);
                wikiContent.setPreset(preset);
            } catch (WikiPresetNotFoundException e) {
                preset = new Preset();
            }
            wikiPageInterpreter.mapContent(wikiContent);
            if (!preset.getOuterContentNames().isEmpty()) {
                getOuterContent(wikiContent, preset, scpBranch, scpLanguage, webClient);
            }
            return wikiContent;
        } catch (IOException e) {
            throw new SCPWikiContentNotFound("Wiki content has not been found: " + name + ", " + scpBranch + ", " + scpLanguage, e);
        }
    }


    private void getOuterContent(WikiContent wikiContent, Preset preset, SCPBranch branch, SCPLanguage translation, WebClient webClient) {
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
