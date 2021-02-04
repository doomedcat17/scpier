package data.appendencies;

import com.fasterxml.jackson.annotation.JsonIgnore;

import data.content_node.ContentNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Appendix implements Serializable {

    private String title = "";

    private List<ContentNode<?>> contents = new ArrayList<>();

    public boolean hasTitle() {
        return !title.equals("");
    }

    public void addContentNode(ContentNode<?> contentNode) {
        contents.add(contentNode);
    }

    @JsonIgnore
    public ContentNode<?> getLastContentNode() {
        return contents.get(contents.size() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appendix)) return false;

        Appendix appendix = (Appendix) o;

        if (getTitle() != null ? !getTitle().equals(appendix.getTitle()) : appendix.getTitle() != null) return false;
        return getContents() != null ? getContents().equals(appendix.getContents()) : appendix.getContents() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getContents() != null ? getContents().hashCode() : 0);
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ContentNode<?>> getContents() {
        return contents;
    }

    public void setContents(List<ContentNode<?>> contents) {
        this.contents = contents;
    }

    public Appendix(String title, List<ContentNode<?>> contents) {
        this.title = title;
        this.contents = contents;
    }

    public Appendix() {
    }
}
