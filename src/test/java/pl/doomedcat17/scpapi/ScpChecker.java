package pl.doomedcat17.scpapi;

import pl.doomedcat17.scpapi.data.ScpObject;
import pl.doomedcat17.scpapi.domain.ScpProviderImpl;

public class ScpChecker implements Runnable {

    private int startNumber;

    private int endNumber;

    @Override
    public void run() {
        ScpProviderImpl scpProvider = new ScpProviderImpl();
        for (int i = startNumber; i <= endNumber; i++) {
            StringBuilder scpNumber = new StringBuilder(String.valueOf(i));
            while (scpNumber.length() < 3) {
                scpNumber.insert(0, '0');
            }
            scpNumber.insert(0, "SCP-");
            try {
                ScpObject scpObject = scpProvider.getScpObject(String.valueOf(i));
                if (scpObject.getObjectName() == null || scpObject.getObjectClass() == null) ScpCheck.addInvalidScp(scpNumber.toString());
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
