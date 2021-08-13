package com.doomedcat17.scpier.testbox.wiki;

import com.doomedcat17.scpier.ScpFoundationDataProvider;
import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.data.SCPWikiEmptyContentException;
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFound;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaleList {

    private static final ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();


    private static final Set<Article> tales = new HashSet<>();

    private static final Set<Article> invalidTales = new HashSet<>();

    private static final Set<Article> emptyTales = new HashSet<>();

    private static Iterator<Article> taleIterator;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        for (SCPBranch scpBranch : SCPBranch.values()) {
            System.out.println(scpBranch.toString() + " started");
            if (scpBranch.equals(SCPBranch.ESTONIAN)) continue;
            TitleFinder.findTitles(scpBranch);
            System.out.println(scpBranch + " ended");
            System.out.println("Tales found: "+tales.size());
        }

        tales.forEach(System.out::println);

        System.out.println(tales.size());


        taleIterator = tales.iterator();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new TaleChecker());
        executorService.execute(new TaleChecker());
        executorService.execute(new TaleChecker());
        executorService.execute(new TaleChecker());
        executorService.execute(new TaleChecker());
        executorService.execute(new TaleChecker());
        executorService.execute(new TaleChecker());
        executorService.execute(new TaleChecker());
        executorService.execute(new TaleChecker());
        executorService.execute(new TaleChecker());

        executorService.shutdown();

        if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
            System.out.println("Invalid tales: "+invalidTales.size());

            invalidTales.forEach(System.out::println);

            System.out.println("Empty tales:");

            emptyTales.forEach(System.out::println);
        }

    }

    static class TaleChecker implements Runnable {

        private static int num = 1;

        private final int threadNum;

        @Override
        public void run() {
            System.out.println("TaleChecker " + threadNum + " started!");
            Article tale = getNextTale();
            while (tale != null) {
                try {
                    ScpWikiData scpWikiData = scpFoundationDataProvider.getScpWikiData(tale.getName(), SCPBranch.ENGLISH);
                    if (scpWikiData.getTitle() == null || scpWikiData.getContent().isEmpty() ||
                            scpWikiData.getContent().stream().anyMatch(ContentNode::isEmpty)) addEmptyTale(tale);
                } catch (SCPWikiContentNotFound e) {
                    e.printStackTrace();
                } catch (SCPWikiEmptyContentException e) {
                    addEmptyTale(tale);
                } catch (Exception e) {
                    addInvalidTale(tale);
                } finally {
                    System.out.println(tale);
                    tale = getNextTale();
                }
            }
        }

        public TaleChecker() {
            this.threadNum = num;
            num++;
        }
    }

    public static synchronized void addInvalidTale(Article tale) {
        invalidTales.add(tale);
    }

    public static synchronized void addEmptyTale(Article tale) {
        emptyTales.add(tale);
    }

    public static synchronized void addTales(Set<Article> talesToAdd) {
        tales.addAll(talesToAdd);
    }

    public static synchronized Article getNextTale() {
        if (taleIterator.hasNext()) {
            return taleIterator.next();
        } else return null;
    }

}
