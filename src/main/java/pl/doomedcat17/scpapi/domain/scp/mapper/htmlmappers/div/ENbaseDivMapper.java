package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.div;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;

import java.util.ArrayList;
import java.util.List;

public class ENbaseDivMapper extends DivMapperComponent {
    @Override
    List<Appendix> mapDivContent(Element element) {
        List<Appendix> appendices = new ArrayList<>();
        String[] scpItem = element.getElementsByClass("itemnum EN")
                .get(0)
                .text()
                .split(":");
        Appendix itemAppendix = new Appendix();
        itemAppendix.setTitle(scpItem[0].trim());
        itemAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, scpItem[1].trim()));
        appendices.add(itemAppendix);
        String scpClass = element.getElementsByClass("obj-text")
                .get(0)
                .text().trim();
        Appendix classAppendix = new Appendix();
        classAppendix.setTitle("Object Class");
        classAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, scpClass));
        appendices.add(classAppendix);
        return appendices;
    }
}
