package com.doomedcat17.scpier.testbox.wiki;

public class Article {

    private String name;

    private String source;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;

        Article article = (Article) o;

        return name.equals(article.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
