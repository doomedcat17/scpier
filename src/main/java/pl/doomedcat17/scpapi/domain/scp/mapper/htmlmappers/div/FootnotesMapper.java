package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.div;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;

import java.util.List;

public class FootnotesMapper extends DivMapperComponent {
    @Override
    List<Appendix> mapDivContent(Element element) {
        Appendix appendix = new Appendix();
        appendix.setTitle("Footnotes");
        Elements footnotes = element.select(".footnote-footer");
        for (Element footnote: footnotes) {
            appendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, footnote.text().trim()));
        }
        return List.of(appendix);
    }
}
