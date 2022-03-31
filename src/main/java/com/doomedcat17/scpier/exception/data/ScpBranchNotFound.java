package com.doomedcat17.scpier.exception.data;

public class ScpBranchNotFound extends RuntimeException {
    public ScpBranchNotFound(String branchId) {
        super("ScpBranch not found: "+branchId);
    }
}
