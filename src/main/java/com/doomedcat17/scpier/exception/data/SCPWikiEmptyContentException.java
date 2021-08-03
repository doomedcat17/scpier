package com.doomedcat17.scpier.exception.data;

import com.doomedcat17.scpier.exception.SCPierApiException;

public class SCPWikiEmptyContentException extends SCPierApiException {
    public SCPWikiEmptyContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
