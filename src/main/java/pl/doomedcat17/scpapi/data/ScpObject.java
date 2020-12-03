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

    private List<Appendix<?>> appendices;

    private String source;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScpObject)) return false;

        ScpObject scpObject = (ScpObject) o;

        if (getObjectName() != null ? !getObjectName().equals(scpObject.getObjectName()) : scpObject.getObjectName() != null)
            return false;
        if (getObjectClass() != null ? !getObjectClass().equals(scpObject.getObjectClass()) : scpObject.getObjectClass() != null)
            return false;
        if (getAppendices() != null ? !getAppendices().equals(scpObject.getAppendices()) : scpObject.getAppendices() != null)
            return false;
        return getSource() != null ? getSource().equals(scpObject.getSource()) : scpObject.getSource() == null;
    }

    @Override
    public int hashCode() {
        int result = getObjectName() != null ? getObjectName().hashCode() : 0;
        result = 31 * result + (getObjectClass() != null ? getObjectClass().hashCode() : 0);
        result = 31 * result + (getAppendices() != null ? getAppendices().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        return result;
    }
}
