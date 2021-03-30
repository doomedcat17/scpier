package com.doomedcat17.scpier.mapper.scp_mappers;

import com.doomedcat17.scpier.data.content_node.ImageNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.scp_object.ScpObject;
import java.util.Iterator;
import java.util.List;

public class ScpAppendicesResolver {

    public static void resolveAppendices(ScpObject scpObject, List<Appendix> appendices) {
        Iterator<Appendix> appendicesIterator = appendices.iterator();
        while (appendicesIterator.hasNext()) {
            Appendix appendix = appendicesIterator.next();
            if (!appendix.getContents().isEmpty()) {
                if (appendix.getContents().stream().allMatch(contentNode -> contentNode instanceof ImageNode)) {
                    appendix.getContents().forEach(contentNode -> scpObject.addImage((ImageNode) contentNode));
                    appendicesIterator.remove();
                } else {
                    if (scpObject.getContent().isEmpty() && !appendix.hasTitle()) {
                        appendix.setTitle("HEADING");
                    }
                    scpObject.addContent(appendix);

                }
            }
        }
    }

    public static void appendicesCleanup(List<Appendix> appendices, TitleResolver titleResolver) {
        appendices.removeIf(appendix -> !appendix.hasTitle() || appendix.getContents().size() == 0);
        Iterator<Appendix> iterator = appendices.iterator();
        while (iterator.hasNext()) {
            Appendix appendix = iterator.next();
            String title = appendix.getTitle();
            if (titleResolver.isItemName(title)) {
                iterator.remove();
                break;
            } else if (appendix.getContents().isEmpty()) iterator.remove();
        }
    }
}
