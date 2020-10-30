package pl.doomedcat17.scpapi.raw.content.filters;

import java.util.regex.Matcher;

public class ParagraphFilter extends Filter{

    @Override
    public String[] getContent(String[] lines, int startIndex) {
        String[] output = new String[2];
        String line = lines[startIndex];
        Matcher paragraphStartMatcher = patternBox.getPARAGRAPH_START_PATTERN().matcher(line);
        Matcher paragraphEndMatcher = patternBox.getPARAGRAPH_END_PATTERN().matcher(line); //TODO code refactor
        if (paragraphStartMatcher.find() && paragraphEndMatcher.find()) {
            output[0] = "";
            output[1] = clear(line.substring(paragraphStartMatcher.end(), paragraphEndMatcher.start()));
            lastIndex = startIndex;
            return output;
        } else throw new RuntimeException();
    }
}
