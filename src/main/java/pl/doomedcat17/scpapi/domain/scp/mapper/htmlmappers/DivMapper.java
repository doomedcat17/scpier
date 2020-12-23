package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
//TODO tests needed
public class DivMapper extends HtmlMapper{
    @Override
    public Appendix mapElement(Element element) {
        Appendix mappedAppendix;
        List<Appendix> appendices = mapContent(element);
        if (appendices.size() == 1) {
            mappedAppendix = appendices.get(0);
            if (mappedAppendix.getContents().size() > 1) {
                mappedAppendix.setContents(List.of(new ContentNode<>(ContentNodeType.DIV, mappedAppendix.getContents())));
            }
        } else {
            ContentNode<List<Appendix>> contentNode = new ContentNode<>(ContentNodeType.APPENDICES, appendices);
            mappedAppendix = new Appendix();
            mappedAppendix.addContentNode(contentNode);
        }
        return mappedAppendix;
    }

    private List<Appendix> mapContent(Element element) {
        if (element.hasClass("collapsible-block")) { //footnotes-footer
            return mapCollapsibleBlock(element);
        } else if (element.hasClass("footnotes-footer")) {
            return mapFootnotes(element);
        } else if (element.hasClass("anom-bar-container")) {
            return mapAnonBar(element);
        } else if (element.hasClass("scp-image-block")){
            return mapImageBlock(element);
        } else return mapDefaultDiv(element);
    }

    private List<Appendix> mapAnonBar(Element element) {
        //TODO code refactor
        List<Appendix> appendices = new ArrayList<>();
        Element itemElement = element.selectFirst(".top-left-box");
        Appendix itemNameAppendix = new Appendix();
        itemNameAppendix.setTitle("Item #");
        itemNameAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, "SCP-"+itemElement.select(".number").text()));
        appendices.add(itemNameAppendix);
        Element scpClasses = element.selectFirst(".text-part");
        for (Element classElement: scpClasses.children()) {
            if (classElement.is(".main-class")) {
                Element containmentClassElement = classElement.selectFirst(".contain-class");
                Appendix scpClass = new Appendix();
                scpClass.setTitle(ScpPattern.OBJECT_CLASS.engNormalized);
                String className = containmentClassElement.selectFirst(".class-text").text();
                className = firstLetterToUpper(className).trim(); //class name first letter to uppercase
                scpClass.addContentNode(new ContentNode<>(ContentNodeType.TEXT, className));
                appendices.add(scpClass);
                Element secondaryClassElement = classElement.selectFirst(".second-class");
                String secondaryClassName = secondaryClassElement.selectFirst(".class-text").text().trim();
                if (!secondaryClassName.equals("none")) {
                    secondaryClassName = firstLetterToUpper(secondaryClassName);
                    Appendix secondaryClassAppendix = new Appendix();
                    secondaryClassAppendix.setTitle(ScpPattern.OBJECT_SECONDARY_CLASS.engNormalized);
                    secondaryClassAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, secondaryClassName));
                    appendices.add(secondaryClassAppendix);
                }
            } else {
                String scpClassTitle = "";
                if (classElement.is(".disrupt-class")) {
                    scpClassTitle = ScpPattern.OBJECT_DISRUPT_CLASS.engNormalized;
                } else if (classElement.is(".risk-class")) {
                    scpClassTitle = ScpPattern.OBJECT_RISK_CLASS.engNormalized;
                }
                String scpClassName = classElement.selectFirst(".class-text").text().trim();
                if (!scpClassName.equals("none")) {
                    scpClassName = firstLetterToUpper(scpClassName);
                    Appendix scpClass = new Appendix();
                    scpClass.setTitle(scpClassTitle);
                    scpClass.addContentNode(new ContentNode<>(ContentNodeType.TEXT, scpClassName));
                    appendices.add(scpClass);
                }
            }
        }
        return appendices;
    }

    private List<Appendix> mapFootnotes(Element element) {
        Appendix appendix = new Appendix();
        appendix.setTitle("Footnotes");
        Elements footnotes = element.select(".footnote-footer");
        for (Element footnote: footnotes) {
            appendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, footnote.text().trim()));
        }
        return List.of(appendix);
    }

    private List<Appendix> mapCollapsibleBlock(Element element) {
        Appendix appendix = new Appendix();
        String title = element.selectFirst(".collapsible-block-link").text();
        title = clearCollapsibleBlockTittle(title);
        if (isTittle(title)) appendix.setTitle(title);
        Element collapsibleBlockContent = element.selectFirst(".collapsible-block-content");
        List<ContentNode<?>> contentNodes = mapElementContent(collapsibleBlockContent);
        appendix.setContents(contentNodes);
        if (!appendix.hasTitle()) {
            appendix.setTitle(lookForTittle(contentNodes));
        }
        return new ArrayList<>(List.of(appendix));
    }


    private String clearCollapsibleBlockTittle(String title){
        char firstChar = title.charAt(0);
        if (firstChar == '+' || firstChar == '>' || firstChar == '-') {
            return title.substring(1).trim();
        } else return title;
    }

    private List<Appendix> mapImageBlock(Element element) {
        ImageMapper imageMapper = new ImageMapper();
        Appendix appendix = imageMapper.mapElement(element.selectFirst("img"));
        String imageCaption = element.selectFirst(".scp-image-caption").text().trim();
        ContentNode<Image> contentNode = (ContentNode<Image>) appendix.getContents().get(0);
        contentNode.getContent().setCaption(imageCaption);
        return new ArrayList<>(List.of(appendix));
    }

    private List<Appendix> mapDefaultDiv(Element element) {
        List<ContentNode<?>> contentNodes = mapElementContent(element);
        Appendix appendix = new Appendix();
        appendix.setContents(contentNodes);
        appendix.setTitle(lookForTittle(contentNodes));
        return new ArrayList<>(List.of(appendix));
    }
    private String firstLetterToUpper(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
