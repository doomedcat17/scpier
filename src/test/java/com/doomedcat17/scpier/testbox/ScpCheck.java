package com.doomedcat17.scpier.testbox;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScpCheck {

    private static final Map<String, Throwable> invalidSCPs = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
      //  executorService.execute(new ScpChecker(2, 500));
       // executorService.execute(new ScpChecker(501, 1000));
        executorService.execute(new ScpChecker(1001, 1500));
        executorService.execute(new ScpChecker(1501, 2000));
      /*  executorService.execute(new ScpChecker(2001, 2500));
        executorService.execute(new ScpChecker(2501, 3000));
        executorService.execute(new ScpChecker(3001, 3500));
        executorService.execute(new ScpChecker(3501, 4000));
        executorService.execute(new ScpChecker(4001, 4500));
        executorService.execute(new ScpChecker(4501, 5000));

        */
        executorService.shutdown();

        if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
            System.out.println("Invalid SCPs: "+invalidSCPs.size());
            for (String key: invalidSCPs.keySet()) {
                System.out.println(key+"\nCause: ");
                invalidSCPs.get(key).printStackTrace();
            }
        }

    }

    public static synchronized void addInvalidScp(String scpName, Throwable cause) {
        invalidSCPs.put(scpName, cause);
    }
}
