package pl.doomedcat17.scpapi.http.raw.filter;

import java.util.regex.Matcher;

public class UlListFilter extends Filter {

    public String[] getContent(String[] lines, int startIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex+1; i < lines.length; i++) {
            String line = lines[i];
            Matcher liStartMatcher = patternBox.getLI_START_PATTERN().matcher(line);
            Matcher liEndMatcher = patternBox.getLI_END_MATCHER().matcher(line);
            Matcher ulEndMatcher = patternBox.getUL_END_MATCHER().matcher(line);
            if (liStartMatcher.find()) {
                stringBuilder.append("\n• ");
                if (liEndMatcher.find()) {
                    String content = line.substring(liStartMatcher.end(), liEndMatcher.start());
                    stringBuilder.append(content);
                }
            }  else if (ulEndMatcher.find()) {
                i++;
                lastIndex = i;
                break;
            } else {
                stringBuilder.append(line);
            }
        }
        stringBuilder.append('\n');
        String[] output = new String[2];
        output[0] = "";
        output[1] = clear(stringBuilder.toString());
        return output;
    }
}
