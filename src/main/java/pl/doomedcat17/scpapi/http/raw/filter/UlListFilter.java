package pl.doomedcat17.scpapi.http.raw.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UlListFilter {

    private final Pattern START_LI_PATTERN = Pattern.compile("(<li>)");

    private final Pattern END_LI_PATTERN = Pattern.compile("(<\\/li>)");

    private final Pattern END_UL_PATTERN = Pattern.compile("(<\\/ul>)");

    private int lastIndex = 0;

    public String getListContent(String[] lines, int startIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex+1; i < lines.length; i++) {
            String line = lines[i];
            Matcher firstMatcher = START_LI_PATTERN.matcher(line);
            Matcher secondMatcher = END_LI_PATTERN.matcher(line);
            Matcher endMatcher = END_UL_PATTERN.matcher(line);
            if (firstMatcher.find()) {
                stringBuilder.append("\n• ");
                if (secondMatcher.find()) {
                    String content = line.substring(firstMatcher.end(), secondMatcher.start());
                    stringBuilder.append(content);
                }
            }  else if (endMatcher.find()) {
                lastIndex = i;
                break;
            } else {
                stringBuilder.append(line);
            }
        }
        stringBuilder.append('\n');
        return clear(stringBuilder.toString());
    }
    private String clear(String text) {
        StringBuilder stringBuilder = new StringBuilder(text);
        if (stringBuilder.charAt(0) == '\n') {
            stringBuilder.replace(0, 1, "");
        }
        if (stringBuilder.charAt(stringBuilder.length()-1) == ' ') {
            int length = stringBuilder.length();
            stringBuilder.replace(length-1, length, "");
        }
        return stringBuilder.toString();
    }

    public int getLastIndex() {
        return lastIndex;
    }
}
