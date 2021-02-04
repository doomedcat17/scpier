package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.line;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import pl.doomedcat17.scpapi.data.content_node.ContentNode;
import pl.doomedcat17.scpapi.data.content_node.ContentNodeType;
import pl.doomedcat17.scpapi.data.content_node.TextNode;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.HtmlScrapper;

import java.util.List;

public class LineScrapper extends HtmlScrapper {
    @Override
    public Appendix scrapElement(Element element) {
        Appendix appendix = new Appendix();
        if (element.is("br")) {
            appendix.addContentNode(new TextNode("\n"));
            return appendix;
        }
        lineCleanup(element);
        if (isNotSimpleLine(element)) {
            String title = extractTitle(element);
            appendix.setTitle(title);
        }
        if (element.hasText()) {
            if (element.parent() != null) {
                List<TextNode> textNodes = textScrapper.scrapText(element);
                if (element.is("p") || element.is("tt")) {
                    textNodes.get(0).setContent(textNodes.get(0).getContent().stripLeading());
                    textNodes.get(textNodes.size() - 1).setContent(textNodes.get(textNodes.size() - 1).getContent().stripTrailing());
                    ContentNode<List<TextNode>> paragraphNode = new ContentNode<>(ContentNodeType.PARAGRAPH, textNodes);
                    appendix.addContentNode(paragraphNode);
                } else textNodes.forEach(appendix::addContentNode);
            }
        }
        return appendix;
    }

    private String extractTitle(Element element) {
        Element strongElement;
        if (!element.tagName().equals("strong")) {
            strongElement = element.selectFirst("strong");
        } else strongElement = element;
        String title = strongElement
                .text().trim();
        strongElement.remove();
        if (title.charAt(title.length() - 1) == ':')
            title = title.substring(0, title.length() - 1); //remove : from the end of title
        return title;
    }

    protected boolean isNotSimpleLine(Element element) {
        if (!element.parent().id().equals(PAGE_CONTENT_ID)) return false; //check
        if (!element.tagName().equals("strong")) {
            Node node = element.childNode(0); //some strong elements are inside text content of element
            if (node instanceof Element) element = (Element) node;
            else return false;
        }
        if (element.tagName().equals("strong")) {
            return titleFinder.isTittle(element.text());
        } else return false;
    }

    //in some cases, first node of the paragraph is empty, this method takes care about it
    private void lineCleanup(Element element) {
        String firstNodeText = element.childNodes().get(0).toString();
        //removing first node if empty or has only Unicode Byte Order Mark
        if (firstNodeText.length() == 0 || (firstNodeText.charAt(0) == 65279 && firstNodeText.length() == 1)) {
            element.childNodes().get(0).remove();
        }
    }
}
