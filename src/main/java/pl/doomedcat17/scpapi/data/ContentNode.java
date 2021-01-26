package pl.doomedcat17.scpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ContentNode<T> {

    protected ContentNodeType contentNodeType;

    protected T content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentNode)) return false;

        ContentNode<?> that = (ContentNode<?>) o;

        if (getContentNodeType() != that.getContentNodeType()) return false;
        return getContent() != null ? getContent().equals(that.getContent()) : that.getContent() == null;
    }

    @Override
    public int hashCode() {
        int result = getContentNodeType() != null ? getContentNodeType().hashCode() : 0;
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        return result;
    }

    public ContentNode(ContentNodeType contentNodeType) {
        this.contentNodeType = contentNodeType;
    }
}
