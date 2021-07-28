package com.doomedcat17.scpier.exception;

import com.doomedcat17.scpier.data.scp.SCPBranch;

public class PresetNotFoundException extends Exception{

    public PresetNotFoundException(String name, SCPBranch scpBranch) {
        super("Preset not found for : "+name+" "+scpBranch.identifier);
    }
}
