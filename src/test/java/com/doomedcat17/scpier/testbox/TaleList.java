package com.doomedcat17.scpier.testbox;

import com.doomedcat17.scpier.ScpFoundationDataProvider;
import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.data.SCPWikiEmptyContentException;
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFound;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaleList {

    private static ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();

    private static Set<String> invalidTales = new HashSet<>();

    private static Set<String> emptyTales = new HashSet<>();

    private static Iterator<String> taleIterator;

    public static void main(String[] args) throws IOException, InterruptedException {
        Set<String> tales = new HashSet<>();
        for (int i = 1; i <= 4; i++) {
            findAllTaleTitles("https://scp-wiki.wikidot.com/incident-reports-eye-witness-interviews-and-personal-logs/p/"+i,
                    tales);
        }
        for (int i = 1; i <= 5; i++) {
            findAllTaleTitles("https://scp-wiki.wikidot.com/series-archive/p/"+i,
                    tales);
        }
        findAllTaleTitles("https://scp-wiki.wikidot.com/creepy-pasta", tales);
        findAllTaleTitles("https://scp-wiki.wikidot.com/tales-by-title", tales);
        findAllTaleTitles("https://scp-wiki.wikidot.com/explained-scps-tales-edition", tales);
        findAllTaleTitles("https://scp-wiki.wikidot.com/joke-scps-tales-edition", tales);
        findAllTaleTitles("https://scp-wiki.wikidot.com/goi-formats", tales);
        tales.add("groups-of-interest");
        tales.add("log-of-extranormal-events");
        tales.add("log-of-unexplained-locations");
        tales.add("object-classes");
        tales.add("personnel-and-character-dossier");
        tales.add("security-clearance-levels");
        tales.add("secure-facilities-locations");
        tales.add("about-the-scp-foundation");
        tales.add("task-forces");

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
            System.out.println("TaleChecker "+threadNum+" started!");
            String tale = getNextTale();
            while (tale != null) {
                try {
                    ScpWikiData scpWikiData = scpFoundationDataProvider.getScpWikiData(tale, SCPBranch.ENGLISH);
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

    public static synchronized void addInvalidTale(String tale) {
        invalidTales.add(tale);
    }

    public static synchronized void addEmptyTale(String tale) {
        emptyTales.add(tale);
    }

    public static synchronized String getNextTale() {
        if (taleIterator.hasNext()) {
            return taleIterator.next();
        } else return null;
    }

    private static void findAllTaleTitles(String url, Set<String> tales) throws IOException {
        Document document = Jsoup.connect(url).get();
        Element content = document.selectFirst("#page-content");
        Elements elements = content.select("a");
        elements.stream().filter(element -> element.attr("href").startsWith("/"))
                .forEach(element -> tales.add(element.attr("href").substring(1)));
    }
}
