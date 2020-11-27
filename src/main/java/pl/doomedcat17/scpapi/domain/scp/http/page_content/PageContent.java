package pl.doomedcat17.scpapi.domain.scp.http.page_content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.nodes.Element;

@Getter
@AllArgsConstructor
public class PageContent {

    private Element content;

    private String sourceUrl;
}
