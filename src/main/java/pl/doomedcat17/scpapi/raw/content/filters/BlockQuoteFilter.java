package pl.doomedcat17.scpapi.raw.content.filters;

import java.util.regex.Matcher;

public class BlockQuoteFilter extends Filter {

    public String[] getContent(String[] lines, int startIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex+1; i < lines.length; i++) {
            String line = lines[i];
            Matcher blockquoteEndMatcher = patternBox.getBLOCKQUOTE_END_PATTERN().matcher(line);
            Matcher paragraphStartMatcher = patternBox.getPARAGRAPH_START_PATTERN().matcher(line);
            Matcher paragraphEndMatcher = patternBox.getPARAGRAPH_END_PATTERN().matcher(line);
            Matcher brMatcher = patternBox.getBR_PATTERN().matcher(line);
            if (paragraphStartMatcher.find()) {
                if (paragraphEndMatcher.find()) {
                    String content = line.substring(paragraphStartMatcher.end(), paragraphEndMatcher.start());
                    stringBuilder.append(content);
                }
            }  else if (blockquoteEndMatcher.find()) {
                lastIndex = i;
                break;
            } else if (!brMatcher.find()){
                stringBuilder.append(line);
            }
        }
        String[] output = new String[2];
        output[0] = "";
        output[1] = clear(stringBuilder.toString());
        stringBuilder.insert(0, '\n');
        return output;
    }

}
