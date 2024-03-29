package com.doomedcat17.scpier.page.html.document.provider.offset;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.doomedcat17.scpier.page.html.document.revision.LastRevisionDateScraper;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OffsetsProvider {

    private static final Set<OffsetPattern> patterns = ResourcesProvider.getOffsetPatterns();


    public static List<Node> getOffsetsContent(WikiContent originalContent, WikiPageProvider wikiPageProvider) {
        List<Node> offsetsContent = new ArrayList<>();
        patterns.forEach(offsetPattern ->
                offsetsContent.addAll(getOffsetContent(originalContent, wikiPageProvider, offsetPattern)));
        return offsetsContent;
    }

    private static List<Node> getOffsetContent(WikiContent originalContent,
                                        WikiPageProvider wikiPageProvider,
                                        OffsetPattern offsetPattern) {
        List<Node> offsetsContent = new ArrayList<>();
        Element previousContent = originalContent.getContent().selectFirst("#page-content");
        LastRevisionDateScraper lastRevisionDateScraper = new LastRevisionDateScraper();
        for (int i = offsetPattern.getMin(); i <= offsetPattern.getMax(); i++) {
            String source = originalContent.getContentSource()+
                    offsetPattern.getPattern().replaceAll("<NUMBER>", String.valueOf(i));
            try {
                WikiContent offsetWikiContent = wikiPageProvider.getWebpageContentWithoutRunningJs(source);
                LocalDateTime lastRevisionTimestamp = lastRevisionDateScraper.scrapDate(offsetWikiContent.getContent());
                Element content = offsetWikiContent.getContent().selectFirst("#page-content");
                if (previousContent.html().equals(content.html())) break;
                offsetsContent.addAll(content.childNodes());
                if (lastRevisionTimestamp.isAfter(originalContent.getLastRevisionDate())) {
                    originalContent.setLastRevisionDate(lastRevisionTimestamp);
                }
                previousContent = content;
            } catch (IOException | RevisionDateException e) {
                break;
            }
        }
        return offsetsContent;

    }


}
