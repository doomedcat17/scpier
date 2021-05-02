package com.doomedcat17.scpier.exception;

public class ScpTaleNotFoundException extends Exception {

    public ScpTaleNotFoundException(String message) {
        super("Could not find scpTale: "+message);
    }
}
