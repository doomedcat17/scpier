package com.doomedcat17.scpier.page.html.document.cleaner;

import org.jsoup.nodes.Element;

import java.util.List;

public interface WikiContentCleaner {

    void clearContentAndUnpackBlocks(Element content);

    void removeTrash(Element content);

    void additionalRemovalDefinitions(List<String> definitions);
}
