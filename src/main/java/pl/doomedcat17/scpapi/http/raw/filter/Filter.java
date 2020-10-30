package pl.doomedcat17.scpapi.http.raw.filter;

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
        StringBuilder stringBuilder = new StringBuilder(cleanedText);
        if (stringBuilder.charAt(0) == '\n' || text.charAt(0) == ' ') {
            stringBuilder.replace(0, 1, "");
        }
        if (stringBuilder.charAt(stringBuilder.length()-1) == ' ' || stringBuilder.charAt(stringBuilder.length()-1) == ' ') {
            int length = stringBuilder.length();
            stringBuilder.replace(length-1, length, "");
        }
        return stringBuilder.toString();
    }
}
