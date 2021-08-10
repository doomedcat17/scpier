package com.doomedcat17.scpier.testbox;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScpCheck {

    private static final Map<String, Throwable> invalidSCPs = new HashMap<>();

    private static final Set<String> emptySCPs = new HashSet<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ScpChecker(2, 250));
        executorService.execute(new ScpChecker(251, 500));
        executorService.execute(new ScpChecker(501, 1000));
        executorService.execute(new ScpChecker(1001, 1250));
        executorService.execute(new ScpChecker(1251, 1500));
        executorService.execute(new ScpChecker(1501, 1750));
        executorService.execute(new ScpChecker(1751, 2000));
        executorService.execute(new ScpChecker(2001, 2250));
        executorService.execute(new ScpChecker(2251, 2500));
        executorService.execute(new ScpChecker(2501, 2750));
        executorService.execute(new ScpChecker(2751, 3000));
        executorService.execute(new ScpChecker(3001, 3250));
        executorService.execute(new ScpChecker(3251, 3500));
        executorService.execute(new ScpChecker(3501, 3750));
        executorService.execute(new ScpChecker(3751, 4000));
        executorService.execute(new ScpChecker(4001, 4250));
        executorService.execute(new ScpChecker(4251, 4500));
        executorService.execute(new ScpChecker(4501, 4750));
        executorService.execute(new ScpChecker(4751, 5000));
        executorService.execute(new ScpChecker(5001, 5250));
        executorService.execute(new ScpChecker(5251, 5500));
        executorService.execute(new ScpChecker(5501, 5750));
        executorService.execute(new ScpChecker(5751, 6000));
        executorService.execute(new ScpChecker(6001, 6250));
        executorService.execute(new ScpChecker(6251, 6500));
        executorService.execute(new ScpChecker(6501, 6750));
        executorService.execute(new ScpChecker(6751, 7000));

        executorService.shutdown();

        if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
            System.out.println("Invalid SCPs: " + invalidSCPs.size());
            for (String key : invalidSCPs.keySet()) {
                System.out.println(key + "\nCause: ");
                invalidSCPs.get(key).printStackTrace();
            }

            System.out.println("Empty SCPs: " + emptySCPs.size());
            for (String key : emptySCPs) {
                System.out.println(key);
            }
        }

    }

    public static synchronized void addInvalidScp(String scpName, Throwable cause) {
        invalidSCPs.put(scpName, cause);
    }

    public static synchronized void addEmptyScp(String scpName) {
        emptySCPs.add(scpName);
    }

}
