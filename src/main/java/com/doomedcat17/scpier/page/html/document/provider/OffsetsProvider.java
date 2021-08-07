package com.doomedcat17.scpier.page.html.document.provider;

import com.doomedcat17.scpier.page.WikiContent;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OffsetsProvider {
    public static List<Node> getOffsets(WikiContent originalContent, String url, WikiPageProvider wikiPageProvider) {
        List<Node> offsetsContent = new ArrayList<>();
        Element previousContent = originalContent.getContent().selectFirst("#page-content");
        for (int i = 1; i <= 5; i++) {
            try {
                WikiContent wikiContent = wikiPageProvider.getWebpageContent(url+"/offset/"+i);
                Element content = wikiContent.getContent().selectFirst("#page-content");
                if (previousContent.html().equals(content.html())) {
                    for (int j = 2; j <= 5; j++) {
                        wikiContent = wikiPageProvider.getWebpageContent(url+"/offset/1/page"+j+"_limit/1");
                        content = wikiContent.getContent().selectFirst("#page-content");
                        if (previousContent.html().equals(content.html())) break;
                        offsetsContent.addAll(content.childNodes());
                        previousContent = content;
                    }
                }
                offsetsContent.addAll(content.childNodes());
                previousContent = content;
            } catch (IOException e) {
                break;
            }
        }
        return offsetsContent;
    }
}
