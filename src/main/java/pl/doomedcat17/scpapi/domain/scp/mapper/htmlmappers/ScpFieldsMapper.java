package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.ScpObject;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;

@AllArgsConstructor
public class ScpFieldsMapper {

    private Elements elements;

    public void mapScp(ScpObject scpObject) {
        setScpAppendices(scpObject);
        setScpNameAndClass(scpObject);
    }

    private void setScpNameAndClass(ScpObject scpObject) {

    }

    private void setScpAppendices(ScpObject scpObject) {
        for (Element element: elements) {
            try {
                HtmlMapper htmlMapper = HtmlMapperFactory.getHtmlMapper(element);
                htmlMapper.mapElement(element, scpObject);
            } catch (MapperNotFoundException ignored) {
            }
        }
    }
}
