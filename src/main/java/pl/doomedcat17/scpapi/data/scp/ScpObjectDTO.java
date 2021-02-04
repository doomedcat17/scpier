package pl.doomedcat17.scpapi.data.scp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import pl.doomedcat17.scpapi.data.appendencies.AppendixDTO;
import pl.doomedcat17.scpapi.data.content_node.Image;

import java.util.ArrayList;
import java.util.List;
public class ScpObjectDTO {

    private String objectName;

    private List<AppendixDTO> appendices = new ArrayList<>();

    private List<Image> images = new ArrayList<>();

    private String source;

    public static ScpObjectDTO apply(ScpObject scpObject) {
        return new ScpObjectDTO(
                scpObject.getObjectName(),
                applyAppendices(scpObject.getAppendices()),
                scpObject.getImages(),
                scpObject.getSource()
        );
    }

    private static List<AppendixDTO> applyAppendices(List<Appendix> appendices) {
        ArrayList<AppendixDTO> appliedAppendices = new ArrayList<>();
        for (Appendix appendix: appendices) {
            appliedAppendices.add(AppendixDTO.apply(appendix));
        }
        return appliedAppendices;
    }

    public ScpObjectDTO(String objectName, List<AppendixDTO> appendices, List<Image> images, String source) {
        this.objectName = objectName;
        this.appendices = appendices;
        this.images = images;
        this.source = source;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<AppendixDTO> getAppendices() {
        return appendices;
    }

    public void setAppendices(List<AppendixDTO> appendices) {
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
}
