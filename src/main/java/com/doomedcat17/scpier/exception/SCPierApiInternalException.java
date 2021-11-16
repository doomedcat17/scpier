package com.doomedcat17.scpier.exception;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPLanguage;

public class SCPierApiInternalException extends SCPierApiException {
    public SCPierApiInternalException(String name, SCPBranch scpBranch, SCPLanguage scpLanguage, Throwable cause) {
        super("SCPier internal error: "+name+", "+scpBranch+", "+ scpLanguage +". Please, submit following case in Issues section of SCPier repository: https://github.com/doomedcat17/scpier/issues", cause);
    }


}
