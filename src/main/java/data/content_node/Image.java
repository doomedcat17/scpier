package data.content_node;

import java.io.Serializable;

public class Image implements Serializable {

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Image(String source, String caption) {
        this.source = source;
        this.caption = caption;
    }

    public Image() {
    }
}
