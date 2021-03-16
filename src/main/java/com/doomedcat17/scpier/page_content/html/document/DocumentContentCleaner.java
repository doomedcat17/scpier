package com.doomedcat17.scpier.page_content.html.document;

import org.jsoup.nodes.Element;

public interface DocumentContentCleaner {

    void clearContentAndUnpackBlocks(Element content);

    void removeTrash(Element content);
}
