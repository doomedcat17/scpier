package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.*;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;
import java.util.List;

@Slf4j
//TODO tests needed
public class DivMapper extends HtmlMapper{
    @Override
    public Appendix mapElement(Element element) { //TODO implementation needed
    /*    Appendix appendix = new Appendix();
        DeletedContentMarker.markDeletedContent(element);
        mapContent(appendix, element);
        if (appendix.hasTitle()) {
            scpObject.addAppendix(appendix);
        } else appendix.getContents()
                .forEach(
                        contentBox ->
                                scpObject.getLastAppendix().addContentBox(contentBox)

                );

     */
        return null;

    }

    private void mapContent(Appendix appendix, Element element, ScpObject scpObject) {
        if (element.hasClass("collapsible-block")) { //footnotes-footer
            mapCollapsibleBlock(appendix, element, scpObject);
        } else if (element.hasClass("footnotes-footer")) {
            mapFootnotes(appendix, element);
        } else if (element.hasClass("anom-bar")) {
            mapHeadingBar(appendix, element, scpObject);
        } else mapDefaultDiv(appendix, element, scpObject);
    }

    private void mapHeadingBar(Appendix appendix, Element element, ScpObject scpObject) {
        //TODO code refactor
        Element itemElement = element.selectFirst("top-left-box");
        appendix = new Appendix();
        appendix.setTitle(itemElement.selectFirst("item").text());
        appendix.addContentBox(new ContentNode<>(ContentNodeType.TEXT, itemElement.select("number")));
        Element scpClasses = element.selectFirst("text-part");
        for (Element classElement: scpClasses.children()) {
            if (classElement.is("main-class")) {
                Element containmentClassElement = classElement.selectFirst("contain-class");
                Appendix scpClass = new Appendix();
                scpClass.setTitle(ScpPattern.OBJECT_CLASS.engNormalized);
                String className = containmentClassElement.selectFirst("class-text").text();
                className = firstLetterToUpper(className).trim(); //class name first letter to uppercase
                scpClass.addContentBox(new ContentNode<>(ContentNodeType.TEXT, className));
                scpObject.addAppendix(scpClass);
                Element secondaryClassElement = classElement.selectFirst("second-class");
                String secondaryClassName = secondaryClassElement.selectFirst("class-text").text().trim();
                if (!secondaryClassName.equals("none")) {
                    secondaryClassName = firstLetterToUpper(secondaryClassName);
                    Appendix secondaryClassAppendix = new Appendix();
                    secondaryClassAppendix.setTitle(ScpPattern.OBJECT_SECONDARY_CLASS.engNormalized);
                    secondaryClassAppendix.addContentBox(new ContentNode<>(ContentNodeType.TEXT, secondaryClassName));
                }
            } else {
                String scpClassTitle = "";
                if (element.is("disrupt-class")) {
                    scpClassTitle = ScpPattern.OBJECT_DISRUPT_CLASS.engNormalized;
                } else if (element.is("risk-class")) {
                    scpClassTitle = ScpPattern.OBJECT_RISK_CLASS.engNormalized;
                }
                String scpClassName = element.selectFirst("class-text").text();
                if (!scpClassName.equals("none")) {
                    scpClassName = firstLetterToUpper(scpClassName).trim();
                    Appendix scpClass = new Appendix();
                    scpClass.setTitle(scpClassTitle);
                    scpClass.addContentBox(new ContentNode<>(ContentNodeType.TEXT, scpClassName));
                }
            }
        }
    }

    private void mapFootnotes(Appendix appendix, Element element) {
        appendix.setTitle("Footnotes");
        Elements footnotes = element.select("footnote-footer");
        for (Element footnote: footnotes) {
            appendix.addContentBox(new ContentNode<>(ContentNodeType.TEXT, footnote.text().trim()));
        }

    }

    private void mapCollapsibleBlock(Appendix appendix, Element element, ScpObject scpObject) {
        appendix.setTitle(element.selectFirst("collapsible-block-link").text().trim());
        mapDefaultDiv(appendix, element.selectFirst("collapsible-block-content"), scpObject);


    }

    private void mapDefaultDiv(Appendix appendix, Element element, ScpObject scpObject) {
        for (Element divChild: element.children()) {
            if (divChild.is("p") || divChild.is("strong")) {
                appendix.addContentBox(new ContentNode<>(ContentNodeType.TEXT, element.text().trim()));
            } else if (divChild.is("div") && divChild.children().size() != 0) {
                mapContent(appendix, element, scpObject);
            } else {
                ScpObject dummyScpObject = new ScpObject();
                dummyScpObject.addAppendix(appendix);
                try {
                    HtmlMapper htmlMapper = HtmlMapperFactory.getHtmlMapper(element);
                } catch (MapperNotFoundException e) {
                    log.info(e.getMessage());
                }
                appendix = dummyScpObject.getLastAppendix();
                List<Image> dummyImages = dummyScpObject.getImages();
                if (!dummyImages.isEmpty()) {
                    dummyImages.forEach(scpObject::addImage);
                }

            }
        }
    }
    private String firstLetterToUpper(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
