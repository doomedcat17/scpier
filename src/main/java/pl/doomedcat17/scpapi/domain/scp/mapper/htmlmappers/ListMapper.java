package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ContentType;
import pl.doomedcat17.scpapi.data.ScpObject;

import java.util.ArrayList;
import java.util.List;

//TODO tests needed
//TODO refactor code
public class ListMapper extends HtmlMapper {
    @Override
    public void mapElement(Element element, ScpObject scpObject) {
        ContentBox<List<ContentBox<?>>> contentBox = new ContentBox<>();
        if (element.tagName().equals("ul")) {
            contentBox.setContentType(ContentType.LIST_UL);
        } else contentBox.setContentType(ContentType.LIST_OL);
        contentBox.setContent(new ArrayList<>());
        mapList(element, contentBox);
        scpObject.getLastAppendix().addContentBox(contentBox);
    }

    private void mapList(Element element, ContentBox<List<ContentBox<?>>> contentBox) {
        Elements children = element.children();
        children.forEach(
                child -> contentBox.getContent().add(mapRow(child))
        );
    }
    private ContentBox<?> mapRow(Element row) {
        if (row.is("ul, ol")) {
            ScpObject dummyScp = getDummyScpObject();
            mapElement(row, dummyScp);
            return dummyScp.getLastAppendix().getLastContentBox();
        } else {
            List<ContentBox<String>> textContentBoxes = scrapText(row);
            if (textContentBoxes.size() > 1) {
                return new ContentBox<>(ContentType.TEXT_ELEMENTS, textContentBoxes);
            } else return new ContentBox<>(ContentType.TEXT, textContentBoxes.get(0));
        }

    }

}
