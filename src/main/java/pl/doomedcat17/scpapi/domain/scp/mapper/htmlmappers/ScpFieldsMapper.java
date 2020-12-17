package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ScpObject;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;

import java.util.Iterator;

@AllArgsConstructor
@Slf4j
public class ScpFieldsMapper {

    private Elements elements;

    public void mapScp(ScpObject scpObject) {
        setScpAppendices(scpObject);
        setScpNameAndClass(scpObject);
    }

    private void setScpNameAndClass(ScpObject scpObject) {
        Iterator<Appendix> iterator = scpObject.getAppendices().iterator();
        while (iterator.hasNext() && (scpObject.getObjectClass() == null || scpObject.getObjectName() == null)) {
            Appendix appendix = iterator.next();
            String title = appendix.getTitle();
            ContentNode<String> contentNode;
            if (title.equals(ScpPattern.OBJECT_CLASS.engNormalized)
                    || appendix.getTitle().equals(ScpPattern.CONTAINMENT_CLASS.engNormalized)) {
                contentNode = (ContentNode<String>) appendix.getLastContentBox();
                scpObject.setObjectClass(contentNode.getContent());
                iterator.remove();
            } else if(title.equals(ScpPattern.OBJECT_NAME.engNormalized))  {
                contentNode = (ContentNode<String>) appendix.getLastContentBox();
                scpObject.setObjectName(contentNode.getContent());
                iterator.remove();
            }
        }

    }

    private void setScpAppendices(ScpObject scpObject) {
        for (Element element: elements) {
            try {
                HtmlMapper htmlMapper = HtmlMapperFactory.getHtmlMapper(element);
                Appendix appendix = htmlMapper.mapElement(element);
                if (!appendix.hasTitle()) {
                    Appendix lastAppendix = scpObject.getLastAppendix();
                    appendix.getContents().forEach(
                            lastAppendix::addContentBox);
                } else scpObject.addAppendix(appendix);
            } catch (MapperNotFoundException e) {
                log.info(e.getMessage());
            }
        }
    }
}
