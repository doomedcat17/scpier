package pl.doomedcat17.scpapi.raw.content.filters;

import pl.doomedcat17.scpapi.raw.content.filters.pattern.PatternBox;

import java.util.regex.Matcher;

public abstract class Filter {

    protected PatternBox patternBox = new PatternBox();

    protected int lastIndex = 0;

    public abstract String[] getContent(String[] lines, int startIndex);

    public int getLastIndex() {
        return lastIndex;
    }

    protected String clear(String text) {
        String cleanedText = text;
        Matcher strongStartMatcher = patternBox.getSTRONG_START_PATTERN().matcher(text);
        if (strongStartMatcher.find()) {
            cleanedText = strongStartMatcher.replaceAll("");
            Matcher strongEndMatcher = patternBox.getSTRONG_END_PATTERN().matcher(cleanedText);
            if (strongEndMatcher.find()) {
                cleanedText = strongEndMatcher.replaceAll("");
            }
        }
        Matcher emStartMatcher = patternBox.getEM_START_PATTERN().matcher(cleanedText);
        if (emStartMatcher.find()) {
            cleanedText = emStartMatcher.replaceAll("");
            Matcher emEndMatcher = patternBox.getEM_END_PATTERN().matcher(cleanedText);
            if (emEndMatcher.find()) {
                cleanedText = emEndMatcher.replaceAll("");
            }
        }
        if (cleanedText.charAt(0) == '\n') {
            StringBuilder sb = new StringBuilder(cleanedText.trim());
            sb.insert(0, '\n');
            cleanedText = sb.toString();
        } else cleanedText = cleanedText.trim();
        return cleanedText;
    }
}
