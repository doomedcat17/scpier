package data.scp_object;

import data.appendencies.Appendix;
import data.content_node.Image;

import java.util.ArrayList;
import java.util.List;

public class ScpObject {

    private String objectName;

    private List<Appendix> appendices = new ArrayList<>();

    private List<Image> images = new ArrayList<>();

    private String source;

    public void addImage(Image image) {
        images.add(image);
    }

    public void addAppendix(Appendix appendix) {
        appendices.add(appendix);
    }

    public Appendix getLastAppendix() {
        return appendices.get(appendices.size() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScpObject)) return false;

        ScpObject scpObject = (ScpObject) o;

        if (getObjectName() != null ? !getObjectName().equals(scpObject.getObjectName()) : scpObject.getObjectName() != null)
            return false;
        if (getAppendices() != null ? !getAppendices().equals(scpObject.getAppendices()) : scpObject.getAppendices() != null)
            return false;
        return getSource() != null ? getSource().equals(scpObject.getSource()) : scpObject.getSource() == null;
    }

    @Override
    public int hashCode() {
        int result = getObjectName() != null ? getObjectName().hashCode() : 0;
        result = 31 * result + (getAppendices() != null ? getAppendices().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        return result;
    }

    public ScpObject() {
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<Appendix> getAppendices() {
        return appendices;
    }

    public void setAppendices(List<Appendix> appendices) {
        this.appendices = appendices;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ScpObject(String objectName, List<Appendix> appendices, List<Image> images, String source) {
        this.objectName = objectName;
        this.appendices = appendices;
        this.images = images;
        this.source = source;
    }
}
