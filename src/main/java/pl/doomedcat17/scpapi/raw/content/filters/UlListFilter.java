package pl.doomedcat17.scpapi.raw.content.filters;

import java.util.regex.Matcher;

public class UlListFilter extends Filter {

    public String[] getContent(String[] lines, int startIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIndex+1; i < lines.length; i++) {
            String line = lines[i];
            if (line.isEmpty()) continue;
            Matcher liStartMatcher = patternBox.getLI_START_PATTERN().matcher(line);
            Matcher liEndMatcher = patternBox.getLI_END_PATTERN().matcher(line);
            Matcher ulStartMatcher = patternBox.getUL_START_PATTERN().matcher(line);
            Matcher ulEndMatcher = patternBox.getUL_END_PATTERN().matcher(line);
            if (liStartMatcher.find()) {
                stringBuilder.append("\n• ");
                String content;
                if (liEndMatcher.find()) {
                    content = line.substring(liStartMatcher.end(), liEndMatcher.start());
                } else {
                    content = line.substring(liStartMatcher.end());
                }
                stringBuilder.append(content);
            } else if (ulStartMatcher.find()){
                String[] nestedListContent = getContent(lines, i);
                stringBuilder.append(nestedListContent[1]);
                i = lastIndex;
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
        StringBuilder sb = new StringBuilder(clear(stringBuilder.toString()));
        sb.insert(0, '\n');
        output[1] = sb.toString();
        return output;
    }
}
