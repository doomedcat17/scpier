package pl.doomedcat17.scpapi.data;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Appendix {

    private String title = "";

    private List<ContentNode<?>> contents = new ArrayList<>();

    public boolean hasTitle() {
        return !title.equals("");
    }

    public void addContentBox(ContentNode<?> contentNode) {
        contents.add(contentNode);
    }

    public ContentNode<?> getLastContentBox() {
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
}
