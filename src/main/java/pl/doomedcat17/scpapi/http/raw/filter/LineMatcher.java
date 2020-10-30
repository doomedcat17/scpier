package pl.doomedcat17.scpapi.http.raw.filter;

import pl.doomedcat17.scpapi.exceptions.MatchNotFoundException;
import java.util.regex.Matcher;

public class LineMatcher {

    private PatternBox patternBox = new PatternBox();

    public Filter matchFilter(String line) throws MatchNotFoundException {
        Filter filter;
        Matcher headerMatcher = patternBox.getHEADER_STRONG_PATTERN().matcher(line);
        Matcher paragraphStartMatcher = patternBox.getPARAGRAPH_START_PATTERN().matcher(line);
        Matcher paragraphEndMatcher = patternBox.getPARAGRAPH_END_PATTERN().matcher(line); //TODO code refactor
        Matcher ulStartMatcher = patternBox.getUL_START_PATTERN().matcher(line);
        Matcher blockQuoteStartMatcher = patternBox.getBLOCKQUOTE_START_PATTERN().matcher(line);
        if (headerMatcher.find()) {
            filter = new HeaderFilter();
        } else if(blockQuoteStartMatcher.find()) {
            filter = new BlockQuoteFilter();
        } else if(ulStartMatcher.find()) {
            filter = new UlListFilter();
        } else if (paragraphStartMatcher.find() && paragraphEndMatcher.find()) {
            filter = new ParagraphFilter();
        } else throw new MatchNotFoundException();
        return filter;
    }
}
