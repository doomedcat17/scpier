package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.*;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;

import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class ScpFieldsMapper {

    private List<Node> nodes;

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
                contentNode = (ContentNode<String>) appendix.getLastContentNode();
                scpObject.setObjectClass(contentNode.getContent());
                iterator.remove();
            } else if(title.equals(ScpPattern.OBJECT_NAME.engNormalized))  {
                contentNode = (ContentNode<String>) appendix.getLastContentNode();
                scpObject.setObjectName(contentNode.getContent());
                iterator.remove();
            }
        }

    }

    private void setScpAppendices(ScpObject scpObject) {
        for (Node node: nodes) {
            if (node.toString().isBlank()) continue;
            if (node instanceof Element) {
                Element element = (Element) node;
                try {
                    HtmlMapper htmlMapper = HtmlMapperFactory.getHtmlMapper(element);
                    Appendix appendix = htmlMapper.mapElement(element);
                    if (!appendix.hasTitle()) {
                        if (element.tagName().equals("div") && appendix.getContents().get(0).getContentNodeType().equals(ContentNodeType.APPENDICES)) {
                            ContentNode<List<Appendix>> contentNode = (ContentNode<List<Appendix>>) appendix.getContents().get(0);
                            contentNode.getContent().forEach(scpObject::addAppendix);
                        } else if (appendix.getContents().get(0).getContentNodeType().equals(ContentNodeType.IMAGE)){
                            ContentNode<Image> imageContentNode = (ContentNode<Image>) appendix.getContents().get(0);
                            scpObject.addImage(imageContentNode.getContent());
                        } else {
                            Appendix lastAppendix = scpObject.getLastAppendix();
                            appendix.getContents().forEach(
                                    lastAppendix::addContentNode);
                        }
                    }  else scpObject.addAppendix(appendix);
                } catch (MapperNotFoundException e) {
                    log.info(e.getMessage());
                }
            } else {
                if (!scpObject.getAppendices().isEmpty()) {
                    if (scpObject.getLastAppendix().getContents().isEmpty()) {
                        scpObject.getLastAppendix().addContentNode(new ContentNode<>(ContentNodeType.TEXT, node.toString()));
                    } else {
                        ContentNode<?> lastContentNode = scpObject.getLastAppendix().getLastContentNode();
                        if (lastContentNode.getContentNodeType().equals(ContentNodeType.TEXT)) {
                            ContentNode<String> lastTextContentNode = (ContentNode<String>) lastContentNode;
                            lastTextContentNode.setContent(lastContentNode.getContent() + node.toString());
                        } else
                            scpObject.getLastAppendix().addContentNode(new ContentNode<>(ContentNodeType.TEXT, node.toString()));
                    }

                }
            }
        }
        scpObject.getAppendices().removeIf(appendix -> !appendix.hasTitle() || appendix.getContents().size() == 0);
    }
}
