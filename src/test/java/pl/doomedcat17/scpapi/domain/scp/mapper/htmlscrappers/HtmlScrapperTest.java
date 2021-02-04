package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import pl.doomedcat17.scpapi.data.content_node.ContentNode;
import pl.doomedcat17.scpapi.data.content_node.ContentNodeType;
import pl.doomedcat17.scpapi.data.content_node.TextNode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class HtmlScrapperTest extends HtmlScrapper {
    @Override
    public Appendix scrapElement(Element element) {
        return null;
    }



}