package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.data.scp.ScpObject;

public class ScpChecker implements Runnable {

    private int startNumber;

    private int endNumber;

    @Override
    public void run() {
        ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();
        for (int i = startNumber; i <= endNumber; i++) {
            StringBuilder scpNumber = new StringBuilder(String.valueOf(i));
            while (scpNumber.length() < 3) {
                scpNumber.insert(0, '0');
            }
            scpNumber.insert(0, "SCP-");
            try {
                ScpObject scpObject = scpFoundationDataProvider.getScpObject(i, SCPBranch.ENGLISH, SCPTranslation.ORIGINAL);
                if (scpObject.getObjectName() == null) ScpCheck.addInvalidScp(scpNumber.toString());
            } catch (Exception e) {
                ScpCheck.addInvalidScp(scpNumber.toString());
            } finally {
                System.out.println(scpNumber.toString());
            }
        }
    }

    public ScpChecker(int startNumber, int endNumber) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
    }
}
