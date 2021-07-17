package com.doomedcat17.scpier.page.html.document;

import com.doomedcat17.scpier.page.PageContent;

import java.io.IOException;

public interface HTMLDocumentProvider {

    PageContent getWebpageContent(String url) throws IOException;
}
