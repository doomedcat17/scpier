package pl.doomedcat17.scpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScpObjectDTO {

    private String objectName;

    private String objectClass;

    private List<AppendixDTO> appendices = new ArrayList<>();

    private List<Image> images = new ArrayList<>();

    private String source;

    public static ScpObjectDTO apply(ScpObject scpObject) {
        return new ScpObjectDTO(
                scpObject.getObjectName(),
                scpObject.getObjectClass(),
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
}
