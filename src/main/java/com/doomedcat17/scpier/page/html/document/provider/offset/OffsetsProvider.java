package com.doomedcat17.scpier.page.html.document.provider.offset;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.doomedcat17.scpier.page.html.document.revision.LastRevisionTimestampProvider.getLastRevisionTimestamp;

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
        for (int i = offsetPattern.getMin(); i <= offsetPattern.getMax(); i++) {
            String source = originalContent.getContentSource()+
                    offsetPattern.getPattern().replaceAll("<NUMBER>", String.valueOf(i));
            try {
                WikiContent offsetWikiContent = wikiPageProvider.getWebpageContent(source);
                Timestamp lastRevisionTimestamp = getLastRevisionTimestamp(offsetWikiContent.getContent());
                Element content = offsetWikiContent.getContent().selectFirst("#page-content");
                if (previousContent.html().equals(content.html())) break;
                offsetsContent.addAll(content.childNodes());
                if (lastRevisionTimestamp.after(originalContent.getLastRevisionTimestamp())) {
                    originalContent.setLastRevisionTimestamp(lastRevisionTimestamp);
                }
                previousContent = content;
            } catch (IOException | RevisionDateException e) {
                break;
            }
        }
        return offsetsContent;

    }


}
