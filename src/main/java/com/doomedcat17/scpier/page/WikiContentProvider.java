package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPLanguage;
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFoundException;
import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import com.doomedcat17.scpier.page.html.document.interpreter.WikiPageInterpreter;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.preset.PresetProvider;
import com.doomedcat17.scpier.page.html.document.provider.DefaultWikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.doomedcat17.scpier.page.html.document.provider.offset.OffsetsProvider;
import com.doomedcat17.scpier.page.html.document.revision.LastRevisionDateScraper;
import com.gargoylesoftware.htmlunit.WebClient;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WikiContentProvider {

    private final PresetProvider presetProvider;

    public WikiContent getPageContent(String name, SCPBranch scpBranch, SCPLanguage scpLanguage, WebClient webClient) throws SCPWikiContentNotFoundException, RevisionDateException {
        try {
            WikiPageProvider wikiPageProvider = new DefaultWikiPageProvider(webClient);
            WikiPageInterpreter wikiPageInterpreter =
                    new WikiPageInterpreter(webClient, wikiPageProvider);
            WikiContent wikiContent = getWikiContent(name, scpBranch, scpLanguage, wikiPageProvider);
            LastRevisionDateScraper lastRevisionDateScraper = new LastRevisionDateScraper();
            wikiContent.setLastRevisionDate(
                    lastRevisionDateScraper.scrapDate(wikiContent.getContent())
            );
            Element content = wikiContent.getContent();
            OffsetsProvider.getOffsetsContent(wikiContent, wikiPageProvider)
                    .forEach(node ->
                            Objects.requireNonNull(content.selectFirst("#page-content"))
                                    .appendChild(node));

            Optional<Preset> foundPreset = presetProvider.getPresetByNameAndBranch(name, scpBranch);
            foundPreset.ifPresent(wikiContent::setPreset);
            wikiPageInterpreter.mapContent(wikiContent);
            if (Objects.nonNull(wikiContent.getPreset()) && !wikiContent.getPreset().getOuterContentNames().isEmpty()) {
                getOuterContent(wikiContent, scpBranch, scpLanguage, webClient);
            }
            return wikiContent;
        } catch (IOException e) {
            throw new SCPWikiContentNotFoundException("Wiki content has not been found: " + name + ", " + scpBranch + ", " + scpLanguage, e);
        }
    }

    private WikiContent getWikiContent(String name, SCPBranch scpBranch, SCPLanguage scpLanguage, WikiPageProvider wikiPageProvider) throws IOException {
        String url = WikiSourceBuilder.buildSource(name.toLowerCase(), scpBranch, scpLanguage);
        name = url.substring(url.lastIndexOf('/') + 1);
        WikiContent wikiContent;
        try {
            wikiContent = wikiPageProvider.getWebpageContentWithoutRunningJs(url);
        } catch (IOException e) {
            //sometimes eng translations are on main foundation page
            if (scpLanguage.equals(SCPLanguage.ENGLISH) && !scpBranch.equals(SCPBranch.ENGLISH)) {
                url = WikiSourceBuilder.buildSource(name, SCPBranch.ENGLISH);
                wikiContent = wikiPageProvider.getWebpageContent(url);
            } else throw e;
        }
        if (!scpLanguage.identifier.equals(scpBranch.identifier)) {
            wikiContent.setTranslationSourceUrl(url);
            wikiContent.setOriginalSourceUrl(
                    WikiSourceBuilder.buildSource(name.toLowerCase(), scpBranch)
            );
        }
        if (wikiContent.getContent().selectFirst("#page-content") == null) throw new IOException();
        wikiContent.setName(name);
        return wikiContent;
    }


    private void getOuterContent(WikiContent wikiContent, SCPBranch branch, SCPLanguage translation, WebClient webClient) {
        List<Node> outerContent = new ArrayList<>();
        Preset preset = wikiContent.getPreset();
        for (String outerContentName : preset.getOuterContentNames())
            try {
                WikiContent outerWikiContent = getPageContent(outerContentName, branch, translation, webClient);
                outerContent.addAll(outerWikiContent.getContent().childNodes());
                if (outerWikiContent.getLastRevisionDate().isAfter(wikiContent.getLastRevisionDate())) {
                    wikiContent.setLastRevisionDate(outerWikiContent.getLastRevisionDate());
                }
            } catch (SCPWikiContentNotFoundException | RevisionDateException ignored) {}
        outerContent.forEach(node -> wikiContent.getContent().appendChild(node));
    }

    public WikiContentProvider() {
        this.presetProvider = ResourcesProvider.getPresetProvider();
    }
}
