package pl.doomedcat17.scpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Appendix<T> {

    private String title;

    private String contentType;

    private T content;


}
