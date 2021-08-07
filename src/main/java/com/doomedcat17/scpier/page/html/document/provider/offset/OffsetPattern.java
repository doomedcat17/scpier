package com.doomedcat17.scpier.page.html.document.provider.offset;

public class OffsetPattern {

    private String pattern;

    private int min;

    private int max;

    public OffsetPattern(String pattern, int min, int max) {
        this.pattern = pattern;
        this.min = min;
        this.max = max;
    }

    public String getPattern() {
        return pattern;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public OffsetPattern() {
    }
}
