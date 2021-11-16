package com.doomedcat17.scpier.exception;

public class SCPierApiException extends Exception{

    public SCPierApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public SCPierApiException(String message) {
        super(message);
    }
}
