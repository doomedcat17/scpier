package com.doomedcat17.scpier.page.webclients;

import com.gargoylesoftware.htmlunit.WebClient;

public class NicelyLimitedWebClient extends RateLimitedWebClient {

    public NicelyLimitedWebClient() {
        super(60, 240);
    }
}
