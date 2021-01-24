package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.div;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.Image;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.ImageMapper;

import java.util.ArrayList;
import java.util.List;

public class ImageBlockMapper extends DivMapperComponent {
    @Override
    List<Appendix> mapDivContent(Element element) {
        ImageMapper imageMapper = new ImageMapper();
        Appendix appendix = imageMapper.mapElement(element.selectFirst("img"));
        String imageCaption = element.selectFirst(".scp-image-caption").text().trim();
        ContentNode<Image> contentNode = (ContentNode<Image>) appendix.getContents().get(0);
        contentNode.getContent().setCaption(imageCaption);
        return new ArrayList<>(List.of(appendix));
    }
}
