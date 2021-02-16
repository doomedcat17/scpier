package mapper;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import data.appendencies.Appendix;
import data.content_node.ContentNode;
import data.content_node.ContentNodeType;
import data.content_node.Image;
import data.scp_object.ScpObject;
import data.scp_object.ScpPattern;
import mapper.htmlscrappers.HtmlScrapper;
import mapper.htmlscrappers.HtmlMapperFactory;
import exceptions.MapperNotFoundException;

import java.util.Iterator;
import java.util.List;

public class ScpFieldsMapper {

    private List<Node> nodes;

    public void mapScp(ScpObject scpObject) {
        setScpAppendices(scpObject);
    }

    private void setScpAppendices(ScpObject scpObject) {
        for (Node node: nodes) {
            if (node.toString().isBlank()) continue;
            if (node instanceof Element) {
                Element element = (Element) node;
                try {
                    HtmlScrapper htmlScrapper = HtmlMapperFactory.getHtmlMapper(element);
                    Appendix appendix = htmlScrapper.scrapElement(element);
                    if (!appendix.hasTitle()) {
                        if (appendix.getContents().isEmpty()) continue;
                        if ((element.tagName().equals("div") || element.tagName().equals("table")) && appendix.getContents().get(0).getContentNodeType().equals(ContentNodeType.APPENDICES)) {
                            ContentNode<List<Appendix>> contentNode = (ContentNode<List<Appendix>>) appendix.getContents().get(0);
                            contentNode.getContent().forEach(scpObject::addAppendix);
                        } else if (appendix.getContents().get(0).getContentNodeType().equals(ContentNodeType.IMAGE)){
                            ContentNode<Image> imageContentNode = (ContentNode<Image>) appendix.getContents().get(0);
                            scpObject.addImage(imageContentNode.getContent());
                        } else if (appendix.getContents().get(0).getContentNodeType().equals(ContentNodeType.HEADING)){
                            ContentNode<Image> imageContentNode = (ContentNode<Image>) appendix.getContents().get(0);
                            scpObject.addImage(imageContentNode.getContent());
                        } else {
                            if (!scpObject.getAppendices().isEmpty()) {
                                addContentNodesToLastAppendix(scpObject, appendix);
                            } else {
                                appendix.setTitle("HEADING");
                                scpObject.addAppendix(appendix);
                            }
                        }
                    } else scpObject.addAppendix(appendix);
                } catch (MapperNotFoundException e) {
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
        appendicesCleanup(scpObject);
    }

    private void appendicesCleanup(ScpObject scpObject) {
        scpObject.getAppendices().removeIf(appendix -> !appendix.hasTitle() || appendix.getContents().size() == 0);
        Iterator<Appendix> iterator = scpObject.getAppendices().iterator();
        while (iterator.hasNext()) {
            Appendix appendix = iterator.next();
            String title = appendix.getTitle();
            if(ScpPattern.isItemName(title, "eng"))  {
                iterator.remove();
                break;
            }
        }
    }

    private void addContentNodesToLastAppendix(ScpObject scpObject, Appendix appendix) {
        for (ContentNode<?> contentNode: appendix.getContents()) {
            if (contentNode.getContent() != null) {
                if (!contentNode.getContent().toString().isBlank()) {
                    scpObject.getLastAppendix().addContentNode(contentNode);
                }
            }
        }
    }

    public ScpFieldsMapper(List<Node> nodes) {
        this.nodes = nodes;
    }
}
