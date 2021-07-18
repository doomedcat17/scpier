package com.doomedcat17.scpier.exception;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;

public class ScpierIternalException extends Exception {
    public ScpierIternalException(String name, SCPBranch scpBranch, SCPTranslation scpTranslation) {
        super("Please, submit this error to scpier github issues section! https://github.com/doomedcat17/scpier/issues\n" +
                "Name: "+name+", Branch: "+scpBranch+", Translation: "+scpTranslation);
    }
}
