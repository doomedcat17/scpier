package com.doomedcat17.scpier.testbox;

import com.doomedcat17.scpier.ScpFoundationDataProvider;
import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPIdentifierPlacement;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.data.SCPWikiEmptyContentException;
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFound;

public class ScpChecker implements Runnable {

    private static int threadCounter = 1;

    private int threadNum;

    private final int startNumber;

    private final int endNumber;

    @Override
    public void run() {
        SCPBranch[] scpBranches = SCPBranch.values();
        for (SCPBranch scpBranch: scpBranches) {
            checkScps(scpBranch , SCPTranslation.ORIGINAL);
        }
    }

    private void checkScps(SCPBranch scpBranch, SCPTranslation scpTranslation) {
        System.out.println("Scp Checker +"+threadNum+" started");
        ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();
        int notFoundCounter = 0;
        for (int i = startNumber; i <= endNumber; i++) {
            if (notFoundCounter == 70) break;
            StringBuilder scpNumber = new StringBuilder(String.valueOf(i));
            while (scpNumber.length() < 3) {
                scpNumber.insert(0, '0');
            }
            if (scpBranch.scpIdentifierPlacement.equals(SCPIdentifierPlacement.MIDDLE)) scpNumber.insert(0, scpBranch.identifier.toUpperCase()+"-");
            scpNumber.insert(0, "SCP-");
            if (scpBranch.scpIdentifierPlacement.equals(SCPIdentifierPlacement.ENDING)) scpNumber.append("-").append(scpBranch.identifier.toUpperCase());
            else scpNumber.append(" ").append(scpTranslation.identifier.toUpperCase());
            try {
                ScpWikiData scpWikiData = scpFoundationDataProvider.getScpWikiData(scpNumber.toString(), scpBranch, scpTranslation);
                if (scpWikiData.getTitle() == null || scpWikiData.getContent().isEmpty() ||
                        scpWikiData.getContent().stream().anyMatch(ContentNode::isEmpty)) ScpCheck.addEmptyScp(scpNumber.toString());
            } catch (SCPWikiContentNotFound e) {
                e.printStackTrace();
                notFoundCounter++;
            } catch (SCPWikiEmptyContentException e){
                ScpCheck.addEmptyScp(scpNumber.toString());
            } catch (Exception e) {
                ScpCheck.addInvalidScp(scpNumber.toString(), e);
            } finally {
                System.out.println(scpNumber);
            }
        }
    }

    public ScpChecker(int startNumber, int endNumber) {
        threadCounter = this.threadNum;
        this.startNumber = startNumber;
        this.endNumber = endNumber;
    }
}
