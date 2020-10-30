package pl.doomedcat17.scpapi.raw.content;

import org.springframework.stereotype.Component;
import pl.doomedcat17.scpapi.exceptions.MatchNotFoundException;
import pl.doomedcat17.scpapi.raw.content.filters.Filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HtmlContentFilterImpl implements HtmlContentFilter {

    LineMatcher lineMatcher = new LineMatcher();

    public String cutHtml(String html) {
        Pattern firstPattern = Pattern.compile("[<][p][>][<](strong)[>](Item #:)[<](\\/strong)[>]( SCP-)\\d\\d\\d[<](\\/p)[>]");
        Pattern secondPattern = Pattern.compile("(<div class=\"footer-wikiwalk-nav\">)");
        Matcher matcher = firstPattern.matcher(html);
        Matcher matcher1 = secondPattern.matcher(html);
        if (matcher.find() && matcher1.find()) { //TODO code refactor
            int firstIndex = matcher.start();
            int secondIndex = matcher1.start();
            secondIndex -= firstIndex;
            html = html.substring(firstIndex);
            return html.substring(0, secondIndex);
        } else throw new RuntimeException("Patterns not found");
    }

    @Override
    public HashMap<String, String> filterHtmlContent(String htmlContent) {
        HashMap<String, String> filteredContent = new HashMap<>();
        htmlContent = cutHtml(htmlContent);
        String[] lines = clearLines(htmlContent.split("\n"));
        String lastKey = "";
        for (int i = 0; i < lines.length; i++) {
            try {
                String line = lines[i];
                Filter filter = lineMatcher.matchFilter(line);
                String[] content = filter.getContent(lines, i);
                if (content[0].equals(lastKey) || content[0].isEmpty()) {
                    String text = filteredContent.get(lastKey);
                    text = text + content[1];
                    filteredContent.put(lastKey, text);
                } else {
                    filteredContent.put(content[0], content[1]);
                    lastKey = content[0];
                }
                i = filter.getLastIndex();
            } catch (MatchNotFoundException e) {
                e.printStackTrace();
            }
        }
        return filteredContent;
    }

    private String[] clearLines(String[] lines) {
        List<String> stringList = new ArrayList<>();
        for (String line: lines) {
            line = line.trim();
            if (!line.isEmpty()) stringList.add(line);
        }
        String[] outputLines = new String[stringList.size()];
        outputLines = stringList.toArray(outputLines);
        return outputLines;
    }


}
