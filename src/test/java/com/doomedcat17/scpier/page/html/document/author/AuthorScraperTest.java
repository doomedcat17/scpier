package com.doomedcat17.scpier.page.html.document.author;

import com.doomedcat17.scpier.page.webclients.NicelyLimitedWebClient;
import org.junit.jupiter.api.Test;
import java.io.IOException;

class AuthorScraperTest {

    @Test
    void meow() throws IOException {
        AuthorScraper authorScraper = new AuthorScraper(new NicelyLimitedWebClient());
        System.out.println(authorScraper.scrap("https://scp-wiki.wikidot.com/scp-049"));
    }

}