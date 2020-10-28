package pl.doomedcat17.scpapi.http.raw.filter;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HtmlContentFilterImpl implements HtmlContentFilter {

    private final Pattern HEADER_PATTERN = Pattern.compile("(<p><strong>).+?(?=<\\/strong>)");

    private final Pattern PARAGRAPH_PATTERN = Pattern.compile("(<p>)");

    private final Pattern END_PARAGRAPH_PATTERN = Pattern.compile("(<\\/p>)");

    private HeaderFilter headerFilter = new HeaderFilter();


    public String cutHtml(String html) {
        Pattern firstPattern = Pattern.compile("[<][p][>][<](strong)[>](Item #:)[<](\\/strong)[>]( SCP-)\\d\\d\\d[<](\\/p)[>]");
        Pattern secondPattern = Pattern.compile("(<div class=\"footer-wikiwalk-nav\">)");
        Matcher matcher = firstPattern.matcher(html);
        Matcher matcher1 = secondPattern.matcher(html);
        if (matcher.find() && matcher1.find()) {
            int firstIndex = matcher.start();
            int secondIndex = matcher1.start();
            secondIndex -= firstIndex;
            html = html.substring(firstIndex);
            return html.substring(0, secondIndex);
        } else throw new RuntimeException("Patterns not found");
    }

    @Override
    public HashMap<String, String> filterHtmlContent(String content) {
        HashMap<String, String> filteredContent = new HashMap<>();
        content = cutHtml(content);
        String[] lines = content.split("\n");
        String lastKey = "";
        int addendumCounter = 0;
        for (String line: lines) {
            Matcher headerMatcher = HEADER_PATTERN.matcher(line);
            Matcher paragraphMatcher = PARAGRAPH_PATTERN.matcher(line);
            Matcher endParagraphMatcher = END_PARAGRAPH_PATTERN.matcher(line);

            if (headerMatcher.find()) {
                String[] header = headerFilter.getHeaderFromLine(line);
                if (header[0].equals("addendum")) {
                    addendumCounter++;
                    header[0] = header[0]+addendumCounter;
                }
                filteredContent.put(header[0], header[1]);
                lastKey = header[0];
            } else if (paragraphMatcher.find() && endParagraphMatcher.find()) {
                String text = line.substring(paragraphMatcher.end(), endParagraphMatcher.start());
                String t = filteredContent.get(lastKey);
                t = t + text;
                filteredContent.put(lastKey, t);
            }
        }
        return filteredContent;
    }


}
