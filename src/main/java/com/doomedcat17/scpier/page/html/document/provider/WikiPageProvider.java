package com.doomedcat17.scpier.page.html.document.provider;

import com.doomedcat17.scpier.page.PageContent;

import java.io.IOException;

public interface WikiPageProvider {

    PageContent getWebpageContent(String url) throws IOException;
}
