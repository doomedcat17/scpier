package domain.scp.mapper.htmlscrappers.div.div_scrapper_componetnts;

import org.jsoup.nodes.Element;
import data.appendencies.Appendix;
import data.content_node.ContentNode;
import data.content_node.ContentNodeType;
import data.scp.ScpPattern;

import java.util.ArrayList;
import java.util.List;

public class AnomBarScrapper extends DivScrapperComponent {

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        List<Appendix> appendices = new ArrayList<>();
        appendices.add(getItemNameAppendix(element));
        Element scpClassesElement = element.selectFirst(".text-part");
        appendices.addAll(getScpClasses(scpClassesElement));
        return appendices;
    }

    private Appendix getItemNameAppendix(Element element) {
        Element itemElement = element.selectFirst(".top-left-box");
        Appendix itemNameAppendix = new Appendix();
        itemNameAppendix.setTitle("Item #");
        itemNameAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, "SCP-"+itemElement.select(".number").text()));
        return itemNameAppendix;
    }

    private List<Appendix> getScpClasses(Element scpClassesElement) {
        List<Appendix> classesAppendices = new ArrayList<>();
        for (Element classElement: scpClassesElement.children()) {
            if (classElement.is(".main-class")) {
                Element containmentClassElement = classElement.selectFirst(".contain-class");
                Appendix scpClass = new Appendix();
                scpClass.setTitle(ScpPattern.OBJECT_CLASS.engNormalized);
                String className = containmentClassElement.selectFirst(".class-text").text();
                className = firstLetterToUpper(className).trim(); //class name first letter to uppercase
                scpClass.addContentNode(new ContentNode<>(ContentNodeType.TEXT, className));
                classesAppendices.add(scpClass);
                Element secondaryClassElement = classElement.selectFirst(".second-class");
                String secondaryClassName = secondaryClassElement.selectFirst(".class-text").text().trim();
                if (!secondaryClassName.equals("none")) {
                    secondaryClassName = firstLetterToUpper(secondaryClassName);
                    Appendix secondaryClassAppendix = new Appendix();
                    secondaryClassAppendix.setTitle(ScpPattern.OBJECT_SECONDARY_CLASS.engNormalized);
                    secondaryClassAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, secondaryClassName));
                    classesAppendices.add(secondaryClassAppendix);
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
                    classesAppendices.add(scpClass);
                }
            }
        }
        return classesAppendices;
    }

    private String firstLetterToUpper(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
