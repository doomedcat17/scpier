package pl.doomedcat17.scpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Appendix {

    private String title = "";

    private List<ContentBox<?>> contents = new ArrayList<>();

    public boolean hasTitle() {
        return !title.equals("");
    }

    public void addContentBox(ContentBox<?> contentBox) {
        contents.add(contentBox);
    }

    public ContentBox<?> getLastContentBox() {
        return contents.get(contents.size() - 1);
    }
}
