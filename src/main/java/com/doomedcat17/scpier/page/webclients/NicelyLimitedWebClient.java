package com.doomedcat17.scpier.page.webclients;

public class NicelyLimitedWebClient extends RateLimitedWebClient {

    public NicelyLimitedWebClient() {
        super(60, 240);
    }
}
