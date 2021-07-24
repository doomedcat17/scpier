package com.doomedcat17.scpier.page.html.document.cleaner;

import org.jsoup.nodes.Element;

public interface DocumentContentCleaner {

    void clearContentAndUnpackBlocks(Element content);

    void removeTrash(Element content);
}
