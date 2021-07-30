package com.doomedcat17.scpier.exception;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;

public class SCPierApiInternalException extends SCPierApiException {
    public SCPierApiInternalException(String name, SCPBranch scpBranch, SCPTranslation scpTranslation, Throwable cause) {
        super("SCPier internal error: "+name+", "+scpBranch+", "+scpTranslation+". Please, submit following case in Issues section of SCPier repository: https://github.com/doomedcat17/scpier/issues", cause);
    }


}
