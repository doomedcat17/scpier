package com.doomedcat17.scpier.testbox;

import com.doomedcat17.scpier.ScpFoundationDataProvider;
import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPIdentifierPlacement;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFound;

public class ScpChecker implements Runnable {

    private final int startNumber;

    private final int endNumber;

    @Override
    public void run() {
        SCPBranch[] scpBranches = SCPBranch.values();
        checkScps(SCPBranch.ENGLISH , SCPTranslation.ORIGINAL);
    }

    private void checkScps(SCPBranch scpBranch, SCPTranslation scpTranslation) {
        ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();
        int notFoundCounter = 0;
        for (int i = startNumber; i <= endNumber; i++) {
            if (notFoundCounter == 40) break;
            StringBuilder scpNumber = new StringBuilder(String.valueOf(i));
            while (scpNumber.length() < 3) {
                scpNumber.insert(0, '0');
            }
            if (scpBranch.scpIdentifierPlacement.equals(SCPIdentifierPlacement.MIDDLE)) scpNumber.insert(0, scpBranch.identifier.toUpperCase()+"-");
            scpNumber.insert(0, "SCP-");
            if (scpBranch.scpIdentifierPlacement.equals(SCPIdentifierPlacement.ENDING)) scpNumber.append("-").append(scpBranch.identifier.toUpperCase());
            if (scpTranslation.equals(SCPTranslation.ORIGINAL)) scpNumber.append(" ORIGINAL");
            else scpNumber.append(" ").append(scpTranslation.identifier.toUpperCase());
            try {
                ScpWikiData scpWikiData = scpFoundationDataProvider.getScpWikiData(String.valueOf(i), scpBranch, scpTranslation);
                if (scpWikiData.getTitle() == null || scpWikiData.getContent().isEmpty() ||
                        scpWikiData.getContent().stream().anyMatch(ContentNode::isEmpty)) ScpCheck.addInvalidScp(scpNumber.toString());
            } catch (SCPWikiContentNotFound e) {
                e.printStackTrace();
                notFoundCounter++;
            } catch (Exception e) {
                ScpCheck.addInvalidScp(scpNumber.toString());
                e.printStackTrace();
            } finally {
                System.out.println(scpNumber);
            }
        }
    }

    public ScpChecker(int startNumber, int endNumber) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
    }
}
