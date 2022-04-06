package com.doomedcat17.scpier.exception.data;

public class ScpLanguageNotFoundException extends RuntimeException {
    public ScpLanguageNotFoundException(String langId) {
        super("Language not found: "+langId);
    }
}
