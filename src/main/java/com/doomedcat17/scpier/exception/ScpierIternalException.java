package com.doomedcat17.scpier.exception;

public class ScpierIternalException extends Exception {
    public ScpierIternalException(String message) {
        super("Please submit this error to scpier github issues section! Exception message: "+message);
    }
}
