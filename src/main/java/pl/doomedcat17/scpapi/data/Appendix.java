package pl.doomedcat17.scpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Appendix<T> {

    private String title;

    private ContentType contentType = ContentType.TEXT;

    private T content;

    private String parentTitle;

    public boolean hasTitle() {
        return title != null;
    }
}
