package mapper.htmlscrappers.div.div_scrapper_componetnts;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import data.appendencies.Appendix;
import data.content_node.ContentNode;
import data.content_node.ContentNodeType;

import java.util.List;

public class FootnotesScrapper extends DivScrapperComponent {
    @Override
    public List<Appendix> scrapDivContent(Element element) {
        Appendix appendix = new Appendix();
        appendix.setTitle("Footnotes");
        Elements footnotes = element.select(".footnote-footer");
        for (Element footnote: footnotes) {
            appendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, footnote.text().trim()));
        }
        return List.of(appendix);
    }
}
