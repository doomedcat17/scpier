package pl.doomedcat17.scpapi.http.raw.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlockQuoteFilter {

    private final Pattern BR_PATTERN = Pattern.compile("(<br>)");

    private final Pattern START_STRONG_PATTERN = Pattern.compile("(<strong>)");

    private final Pattern END_STRONG_PATTERN = Pattern.compile("(<\\/strong>)");

    private final Pattern PARAGRAPH_PATTERN = Pattern.compile("(<p>)");

    private final Pattern END_PARAGRAPH_PATTERN = Pattern.compile("(<\\/p>)");

    private final Pattern END_PATTERN = Pattern.compile("(<\\/blockquote>)");

    private int lastIndex = 0;

    public String getBlockQuoteContent(String[] lines, int startIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex+1; i < lines.length; i++) {
            String line = lines[i];
            Matcher endMatcher = END_PATTERN.matcher(line);
            Matcher startParagraphMatcher = PARAGRAPH_PATTERN.matcher(line);
            Matcher endParagraphMatcher = END_PARAGRAPH_PATTERN.matcher(line);
            Matcher brMatcher = BR_PATTERN.matcher(line);
            if (startParagraphMatcher.find()) {
                if (endParagraphMatcher.find()) {
                    String content = line.substring(startParagraphMatcher.end(), endParagraphMatcher.start());
                    stringBuilder.append(content);
                }
            }  else if (endMatcher.find()) {
                lastIndex = i;
                break;
            } else if (!brMatcher.find()){
                stringBuilder.append(line);
            }
        }
        return clearText(stringBuilder.toString());
    }


    private String clearText(String text) {
        Matcher strongStartMatcher = START_STRONG_PATTERN.matcher(text);
        if (strongStartMatcher.find()) {
            String outputText = strongStartMatcher.replaceAll("");
            Matcher strongEndMatcher = END_STRONG_PATTERN.matcher(outputText);
            if (strongEndMatcher.find()) {
                outputText = strongEndMatcher.replaceAll("");
            }
            return outputText;
        } else return text;
    }

    public int getLastIndex() {
        return lastIndex;
    }
}
