package pl.doomedcat17.scpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ScpObject {

    private String objectName;

    private String objectClass;

    private String description;

    private String containmentProcedures;

    private List<Appendix<?>> appendices;

    private String source;
}
