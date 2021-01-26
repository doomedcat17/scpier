package pl.doomedcat17.scpapi.data;

import java.util.ArrayList;
import java.util.List;

public class TextNode extends ContentNode<String> {

    private List<StyleType> styleTypes = new ArrayList<>();

    public void addStyleTypes(StyleType styleType) {
        styleTypes.add(styleType);
    }

    public TextNode() {
        super(ContentNodeType.TEXT);
    }


}
