package com.doomedcat17.scpier.exception.page.html.document.provider;

public class IframeContentProviderException extends Exception{

    public IframeContentProviderException(Throwable cause) {
        super("Iframe content could not be provided",cause);
    }
}
