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

    private final Pattern UL_START_PATTERN = Pattern.compile("(<ul>)");

    private final Pattern EM_START_PATTERN = Pattern.compile("(<em>)");

    private final Pattern EM_END_PATTERN = Pattern.compile("(<\\/em>)");

    private final Pattern BLOCKQUOTE_START_PATTERN = Pattern.compile("(<blockquote>)");

    private HeaderFilter headerFilter = new HeaderFilter();



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
    public HashMap<String, String> filterHtmlContent(String content) {
        HashMap<String, String> filteredContent = new HashMap<>();
        content = cutHtml(content);
        String[] lines = content.split("\n");
        String lastKey = "";
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            Matcher headerMatcher = HEADER_PATTERN.matcher(line);
            Matcher paragraphMatcher = PARAGRAPH_PATTERN.matcher(line);
            Matcher endParagraphMatcher = END_PARAGRAPH_PATTERN.matcher(line); //TODO code refactor
            Matcher ulMatcher = UL_START_PATTERN.matcher(line);
            Matcher blockQuoteMatcher = BLOCKQUOTE_START_PATTERN.matcher(line);
            if (headerMatcher.find()) {
                String[] header = headerFilter.getHeaderFromLine(line);
                if (header[0].equals(lastKey)) {
                    String t = filteredContent.get(lastKey);
                    t = t + header[1];
                    filteredContent.put(lastKey, t);
                } else {
                    filteredContent.put(header[0], clearText(header[1]));
                    lastKey = header[0];
                }
            } else if(blockQuoteMatcher.find()) {
                BlockQuoteFilter blockQuoteFilter = new BlockQuoteFilter();
                String text = clearText(blockQuoteFilter.getBlockQuoteContent(lines, i));
                i = blockQuoteFilter.getLastIndex();
                String t = filteredContent.get(lastKey);
                t = t + text;
                filteredContent.put(lastKey, t);
            } else if(ulMatcher.find()) {
                UlListFilter ulListFilter = new UlListFilter();
                String text = clearText(ulListFilter.getListContent(lines, i));
                i = ulListFilter.getLastIndex();
                String t = filteredContent.get(lastKey);
                t = t + text;
                filteredContent.put(lastKey, t);
            } else if (paragraphMatcher.find() && endParagraphMatcher.find()) {
                String text = clearText(line.substring(paragraphMatcher.end(), endParagraphMatcher.start()));
                String t = filteredContent.get(lastKey);
                t = t + text;
                filteredContent.put(lastKey, t);
            }
        }
        return filteredContent;
    }

    private String clearText(String text) {
        Matcher emStartMatcher = EM_START_PATTERN.matcher(text);
        if (emStartMatcher.find()) {
            String outputText = emStartMatcher.replaceAll("");
            Matcher emEndMatcher = EM_END_PATTERN.matcher(outputText);
            if (emEndMatcher.find()) {
                outputText = emEndMatcher.replaceAll("");
            }
            return outputText;
        } else return text;
    }


}
