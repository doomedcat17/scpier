package pl.doomedcat17.scpapi.raw.content.filters.pattern;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class PatternBox {

    private final Pattern HEADER_STRONG_PATTERN = Pattern.compile("(<p><strong>).+?(?=<\\/strong>)");

    private final Pattern PARAGRAPH_START_PATTERN = Pattern.compile("(<p>)");

    private final Pattern PARAGRAPH_END_PATTERN = Pattern.compile("(<\\/p>)");

    private final Pattern LI_START_PATTERN = Pattern.compile("(<li>)");

    private final Pattern LI_END_PATTERN= Pattern.compile("(<\\/li>)");

    private final Pattern UL_START_PATTERN = Pattern.compile("(<ul>)");

    private final Pattern UL_END_PATTERN = Pattern.compile("(<\\/ul>)");

    private final Pattern STRONG_START_PATTERN = Pattern.compile("(<strong>)");

    private final Pattern STRONG_END_PATTERN = Pattern.compile("(<\\/strong>)");

    private final Pattern BR_PATTERN = Pattern.compile("(<br>)");

    private final Pattern BLOCKQUOTE_START_PATTERN = Pattern.compile("(<blockquote>)");

    private final Pattern BLOCKQUOTE_END_PATTERN = Pattern.compile("(<\\/blockquote>)");

    private final Pattern EM_START_PATTERN = Pattern.compile("(<em>)");

    private final Pattern EM_END_PATTERN = Pattern.compile("(<\\/em>)");
}
