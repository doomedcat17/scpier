package com.doomedcat17.scpier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScpCheck {

    private static final ArrayList<String> invalidSCPs = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ScpChecker(2, 1000));
        executorService.execute(new ScpChecker(1001, 2000));
        executorService.execute(new ScpChecker(2001, 3000));
        executorService.execute(new ScpChecker(3001, 4000));
        executorService.execute(new ScpChecker(4001, 5000));
        executorService.shutdown();

        if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
            System.out.println("Invalid SCPs: "+invalidSCPs.size());
            Collections.sort(invalidSCPs);
            invalidSCPs.forEach(System.out::println);
        }

    }

    public static synchronized void addInvalidScp(String scpName) {
        invalidSCPs.add(scpName);
    }
}
