package page_content;

import org.jsoup.nodes.Element;

public class PageContent {

    private String scpName;

    private Element content;

    private String sourceUrl;

    public PageContent(String scpName, Element content, String sourceUrl) {
        this.scpName = scpName;
        this.content = content;
        this.sourceUrl = sourceUrl;
    }

    public PageContent() {
    }

    public String getScpName() {
        return scpName;
    }

    public void setScpName(String scpName) {
        this.scpName = scpName;
    }

    public Element getContent() {
        return content;
    }

    public void setContent(Element content) {
        this.content = content;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
