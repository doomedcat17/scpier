package com.doomedcat17.scpier.exception.page.html.document.revision;

public class RevisionDateException extends Exception{

    public RevisionDateException(Throwable cause) {
        super("Could not get last revision date", cause);
    }
}
