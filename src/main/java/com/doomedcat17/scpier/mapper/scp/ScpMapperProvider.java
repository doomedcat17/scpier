package com.doomedcat17.scpier.mapper.scp;


import com.doomedcat17.scpier.exception.SCPWikiContentNotFound;

public class ScpMapperProvider {


    public static ScpWikiContentMapper getScpMapper(String scpName) throws SCPWikiContentNotFound {
        switch (scpName) {
            case "SCP-2212":
                //TODO implement scp mapper
                throw new SCPWikiContentNotFound("Object not compatible");
            default:
                return new DefaultScpWikiContentMapper();
        }
    }
}
