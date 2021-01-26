package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.div;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;

import java.util.ArrayList;
import java.util.List;

public class ACSDivMapper extends DivMapperComponent{
    @Override
    List<Appendix> mapDivContent(Element element) {
        List<Appendix> appendices = new ArrayList<>();
        Element scpItemElement = element.getElementsByClass("acs-item")
                .first().child(0);
        Appendix itemAppendix = new Appendix();
        itemAppendix.setTitle("Item");
        itemAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, "SCP-"+scpItemElement.childNode(1).toString().trim()));
        appendices.add(itemAppendix);
        Element scpClassElement = element.getElementsByClass("acs-contain-container").first().getElementsByClass("acs-text").first();
        Appendix classAppendix = new Appendix();
        classAppendix.setTitle("Object Class");
        classAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, capitalizeText(scpClassElement.child(1).text())));
        appendices.add(classAppendix);
        mapRestOfClasses(element, appendices);
        return appendices;
    }

    private void mapRestOfClasses(Element element, List<Appendix> appendices) {
        mapSecondaryClass(element, appendices);
        mapDisruptClass(element, appendices);
        mapRiskClass(element, appendices);
    }

    private void mapSecondaryClass(Element element, List<Appendix> appendices) {
        Element secondaryClassElement = element.getElementsByClass("acs-secondary").first();
        if (secondaryClassElement != null) {
            String secondaryClassText = secondaryClassElement.getElementsByClass("acs-text")
                    .first()
                    .child(1)
                    .text();
            if (!secondaryClassText.equals("{$secondary-class}")) {
                Appendix secondaryClassAppendix = new Appendix();
                secondaryClassText = capitalizeText(secondaryClassText).trim();
                secondaryClassAppendix.setTitle("Secondary Class");
                secondaryClassAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, secondaryClassText));
                appendices.add(secondaryClassAppendix);
            }
        }
    }

    private void mapDisruptClass(Element element, List<Appendix> appendices) {
        Element disruptClassElement = element.getElementsByClass("acs-disrupt").first();
        if (disruptClassElement != null) {
            String disruptClassText = disruptClassElement.getElementsByClass("acs-text")
                    .first()
                    .childNode(3)
                    .toString();
            if (!disruptClassText.equals("{$disrupt-class}")) {
                if (disruptClassText.charAt(0) == '/') disruptClassText = disruptClassText.substring(1);
                disruptClassText = capitalizeText(disruptClassText).trim();
                Appendix disruptClassAppendix = new Appendix();
                disruptClassAppendix.setTitle("Disruption Class");
                disruptClassAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, disruptClassText));
                appendices.add(disruptClassAppendix);
            }
        }
    }

    private void mapRiskClass(Element element, List<Appendix> appendices) {
        Element riskClassElement = element.getElementsByClass("acs-risk").first();
        if (riskClassElement != null) {
            String riskClassText = riskClassElement.getElementsByClass("acs-text")
                    .first()
                    .childNode(3)
                    .toString();
            if (!riskClassText.equals("{$danger-class}")) {
                if (riskClassText.charAt(0) == '/') riskClassText = riskClassText.substring(1);
                riskClassText = capitalizeText(riskClassText).trim();
                Appendix riskClassAppendix = new Appendix();
                riskClassAppendix.setTitle("Risk Class");
                riskClassAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, riskClassText));
                appendices.add(riskClassAppendix);
            }
        }
    }
}
