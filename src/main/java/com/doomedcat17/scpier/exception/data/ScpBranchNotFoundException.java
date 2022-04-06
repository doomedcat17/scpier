package com.doomedcat17.scpier.exception.data;

public class ScpBranchNotFoundException extends RuntimeException {
    public ScpBranchNotFoundException(String branchId) {
        super("ScpBranch not found: "+branchId);
    }
}
