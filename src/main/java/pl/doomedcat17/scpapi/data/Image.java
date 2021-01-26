package pl.doomedcat17.scpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Image {

    private String source;

    private String caption = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;

        Image image = (Image) o;

        if (getSource() != null ? !getSource().equals(image.getSource()) : image.getSource() != null) return false;
        return getCaption() != null ? getCaption().equals(image.getCaption()) : image.getCaption() == null;
    }

    @Override
    public int hashCode() {
        int result = getSource() != null ? getSource().hashCode() : 0;
        result = 31 * result + (getCaption() != null ? getCaption().hashCode() : 0);
        return result;
    }
}
