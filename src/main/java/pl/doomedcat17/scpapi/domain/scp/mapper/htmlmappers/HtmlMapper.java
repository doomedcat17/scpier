package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ContentType;
import pl.doomedcat17.scpapi.data.ScpObject;

import java.util.ArrayList;
import java.util.List;

public abstract class HtmlMapper {

   public abstract void mapElement(Element element, ScpObject scpObject);

   protected List<ContentBox<String>> scrapText(Element textElement) {
       List<ContentBox<String>> contentBoxes = new ArrayList<>();
       ContentBox<String> contentBox = new ContentBox<>();
       for (Node node: textElement.childNodes()) {
           if (node instanceof Element) {
               Element element = (Element) node;
               if (element.tagName().equals("br")) {
                   addText(contentBox, "\n");
               } else if (element.is("[style*=\"text-decoration: line-through;\"]")) {
                   contentBoxes.add(contentBox);
                   ContentBox<String> deletedText = new ContentBox<>(ContentType.TEXT_DELETED, element.text());
                   contentBoxes.add(deletedText);
                   contentBox = new ContentBox<>();
               } else {
                   addText(contentBox, element.text());
               }
           } else {
               String text = node.toString();
               if (!text.isBlank()) {
                   addText(contentBox, text);
               }
           }
       }
       contentBoxes.add(contentBox);
       trimContent(contentBoxes);
       return contentBoxes;
   }

   private void trimContent(List<ContentBox<String>> contentBoxes) {
       contentBoxes.forEach(contentBox -> contentBox.setContent(contentBox.getContent().trim()));
   }

    private void addText(ContentBox<String> contentBox, String text) {
        if (contentBox.getContent() == null) {
            contentBox.setContent(text);
        } else contentBox.setContent(contentBox.getContent()+text);
    }

    public ScpObject getDummyScpObject() {
       Appendix dummyAppendix = new Appendix();
       dummyAppendix.setTitle("Dummy");
       ScpObject dummyScp = new ScpObject();
       dummyScp.addAppendix(dummyAppendix);
       return dummyScp;
    }

}

