package domain.scp.mapper;

import org.jsoup.nodes.Node;
import data.scp.ScpObject;
import domain.scp.http.page_content.PageContent;

import java.util.List;

public class ScpMapper {
//TODO code refactor
    public ScpObject mapToScp(PageContent pageContent) {
        List<Node> data = pageContent.getContent().childNodes();
        ScpFieldsMapper scpFieldsMapper = new ScpFieldsMapper(data);
        ScpObject scpObject = new ScpObject();
        scpObject.setObjectName(pageContent.getScpName());
        scpFieldsMapper.mapScp(scpObject);
        return scpObject;
    }


}
