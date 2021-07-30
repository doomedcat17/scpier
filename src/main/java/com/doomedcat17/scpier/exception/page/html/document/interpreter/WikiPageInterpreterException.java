package com.doomedcat17.scpier.exception.page.html.document.interpreter;

public class WikiPageInterpreterException extends RuntimeException {
    public WikiPageInterpreterException(Throwable cause) {
        super("Content could not be interpreted", cause);
    }
}
