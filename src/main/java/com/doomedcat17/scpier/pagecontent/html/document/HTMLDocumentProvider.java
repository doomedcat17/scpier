package com.doomedcat17.scpier.pagecontent.html.document;

import com.doomedcat17.scpier.pagecontent.PageContent;

import java.io.IOException;

public interface HTMLDocumentProvider {

    PageContent getWebpageContent(String url) throws IOException;
}
