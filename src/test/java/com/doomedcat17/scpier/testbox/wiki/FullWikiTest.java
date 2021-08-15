package com.doomedcat17.scpier.testbox.wiki;

import com.doomedcat17.scpier.ScpFoundationDataProvider;
import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.exception.data.SCPWikiEmptyContentException;
import com.doomedcat17.scpier.exception.page.SCPWikiContentNotFound;
import com.doomedcat17.scpier.testbox.JSONWriter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FullWikiTest {

    private static final ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();

    private static final Set<Article> invalidTales = new HashSet<>();

    private static final Set<Article> emptyTales = new HashSet<>();

    private static Iterator<Article> taleIterator;

    private static BufferedWriter wikiWriter;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        Set<Article> tales = objectMapper.readValue(new File("src/test/resources/all-articles.json"),
                new TypeReference<>() {
                });
        wikiWriter = new BufferedWriter(new FileWriter("src/test/resources/scp-wiki-data.json"));
        wikiWriter.write("{");
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
            wikiWriter.write("}");
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/resources/full-test-result.text"));
            StringBuilder sb = new StringBuilder("Invalid tales: " + invalidTales.size());

            System.out.println("Invalid tales: " + invalidTales.size());
            sb.append("Invalid tales: ").append(invalidTales.size());
            invalidTales.forEach(tale -> {
                System.out.println(tale);
                sb.append('\n')
                        .append(tale);
            });

            System.out.println("Empty tales:");
            sb.append("Empty tales:");
            emptyTales.forEach(tale -> {
                System.out.println(tale);
                sb.append('\n')
                        .append(tale);
            });
            writer.write(sb.toString());
            writer.close();

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
                    else addArticle(scpWikiData);
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

    public static synchronized void addArticle(ScpWikiData scpWikiData) throws IOException {
        BufferedWriter wikiWriter = new BufferedWriter(new FileWriter("src/test/resources/scp-wiki-data.json"));
        wikiWriter.append(JSONWriter.asJSONString(scpWikiData));
        wikiWriter.append(",");
    }

    public static synchronized Article getNextTale() {
        if (taleIterator.hasNext()) {
            return taleIterator.next();
        } else return null;
    }

}
