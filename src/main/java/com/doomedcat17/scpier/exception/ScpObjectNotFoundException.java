package com.doomedcat17.scpier.exception;

public class ScpObjectNotFoundException extends Exception {

    public ScpObjectNotFoundException(String message) {
        super("Could not find scpObject: "+message);
    }
}
