package com.doomedcat17.scpier.data.scp_object;

import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ImageNode;

import java.util.ArrayList;
import java.util.List;

public class ScpObject {

    private String objectName;

    private List<Appendix> appendices = new ArrayList<>();

    private List<ImageNode> images = new ArrayList<>();

    private List<String> tags;

    private String source;


    public void addImage(ImageNode image) {
        images.add(image);
    }

    public void addAppendix(Appendix appendix) {
        appendices.add(appendix);
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
        if (getImages() != null ? !getImages().equals(scpObject.getImages()) : scpObject.getImages() != null)
            return false;
        if (getTags() != null ? !getTags().equals(scpObject.getTags()) : scpObject.getTags() != null) return false;
        return getSource() != null ? getSource().equals(scpObject.getSource()) : scpObject.getSource() == null;
    }

    @Override
    public int hashCode() {
        int result = getObjectName() != null ? getObjectName().hashCode() : 0;
        result = 31 * result + (getAppendices() != null ? getAppendices().hashCode() : 0);
        result = 31 * result + (getImages() != null ? getImages().hashCode() : 0);
        result = 31 * result + (getTags() != null ? getTags().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        return result;
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

    public List<ImageNode> getImages() {
        return images;
    }

    public void setImages(List<ImageNode> images) {
        this.images = images;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
