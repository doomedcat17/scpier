package com.doomedcat17.scpier.exception;

public class SCPWikiContentNotFound extends SCPierApiException {
    public SCPWikiContentNotFound(String message) {
        super("Requested content is not available: "+message);
    }
}
