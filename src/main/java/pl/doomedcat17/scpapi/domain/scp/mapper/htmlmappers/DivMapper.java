package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ContentType;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
//TODO tests needed
public class DivMapper implements HtmlMapper{
    @Override
    public void mapElement(Element element, List<Appendix> scpAppendices) {
        Appendix appendix = new Appendix();
        DeletedContentMarker.markDeletedContent(element);
        mapContent(appendix, element, scpAppendices);
        if (appendix.hasTitle()) {
            scpAppendices.add(appendix);
        } else appendix.getContents()
                .forEach(
                        contentBox ->
                                scpAppendices.get(scpAppendices.size() -1).addContentBox(contentBox)
                );

    }

    private void mapContent(Appendix appendix, Element element, List<Appendix> scpAppendices) {
        if (element.hasClass("collapsible-block")) { //footnotes-footer
            mapCollapsibleBlock(appendix, element, scpAppendices);
        } else if (element.hasClass("footnotes-footer")) {
            mapFootnotes(appendix, element, scpAppendices);
        } else if (element.hasClass("anom-bar")) {
            mapHeadingBar(appendix, element, scpAppendices);
        } else mapDefaultDiv(appendix, element, scpAppendices);
    }

    private void mapHeadingBar(Appendix appendix, Element element, List<Appendix> scpAppendices) {
        //TODO code refactor
        Element itemElement = element.selectFirst("top-left-box");
        Appendix scpNumber = new Appendix();
        scpNumber.setTitle(itemElement.selectFirst("item").text());
        scpNumber.addContentBox(new ContentBox<>(ContentType.TEXT, itemElement.select("number")));
        scpAppendices.add(scpNumber);
        Element scpClasses = element.selectFirst("text-part");
        for (Element classElement: scpClasses.children()) {
            if (classElement.is("main-class")) {
                Element containmentClassElement = classElement.selectFirst("contain-class");
                Appendix scpClass = new Appendix();
                scpClass.setTitle(ScpPattern.OBJECT_CLASS.engNormalized);
                String className = containmentClassElement.selectFirst("class-text").text();
                className = firstLetterToUpper(className).trim(); //class name first letter to uppercase
                scpClass.addContentBox(new ContentBox<>(ContentType.TEXT, className));
                scpAppendices.add(scpClass);
                Element secondaryClassElement = classElement.selectFirst("second-class");
                String secondaryClassName = secondaryClassElement.selectFirst("class-text").text().trim();
                if (!secondaryClassName.equals("none")) {
                    secondaryClassName = firstLetterToUpper(secondaryClassName);
                    Appendix secondaryClassAppendix = new Appendix();
                    secondaryClassAppendix.setTitle(ScpPattern.OBJECT_SECONDARY_CLASS.engNormalized);
                    secondaryClassAppendix.addContentBox(new ContentBox<>(ContentType.TEXT, secondaryClassName));
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
                    scpClass.addContentBox(new ContentBox<>(ContentType.TEXT, scpClassName));
                }
            }
        }
    }

    private void mapFootnotes(Appendix appendix, Element element, List<Appendix> scpAppendices) {
        appendix.setTitle("Footnotes");
        Elements footnotes = element.select("footnote-footer");
        for (Element footnote: footnotes) {
            appendix.addContentBox(new ContentBox<>(ContentType.TEXT, footnote.text().trim()));
        }

    }

    private void mapCollapsibleBlock(Appendix appendix, Element element, List<Appendix> scpAppendices) {
        appendix.setTitle(element.selectFirst("collapsible-block-link").text().trim());
        mapDefaultDiv(appendix, element.selectFirst("collapsible-block-content"), scpAppendices);


    }

    private void mapDefaultDiv(Appendix appendix, Element element, List<Appendix> scpAppendices) {
        for (Element divChild: element.children()) {
            if (divChild.is("p") || divChild.is("strong")) {
                appendix.addContentBox(new ContentBox<>(ContentType.TEXT, element.text().trim()));
            } else if (divChild.is("div") && divChild.children().size() != 0) {
                mapContent(appendix, element, scpAppendices);
            } else {
                List<Appendix> appendices = new ArrayList<>();
                appendices.add(appendix);
                try {
                    HtmlMapper htmlMapper = HtmlMapperFactory.getHtmlMapper(element);
                    htmlMapper.mapElement(divChild, scpAppendices);
                } catch (MapperNotFoundException e) {
                    log.info(e.getMessage());
                }


            }
        }
    }
    private String firstLetterToUpper(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
