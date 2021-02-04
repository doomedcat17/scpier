package data.appendencies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import data.content_node.ContentNode;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AppendixDTO {

    private String title = "";

    private List<ContentNode<?>> contents = new ArrayList<>();

    public static AppendixDTO apply(Appendix appendix) {
        return new AppendixDTO(appendix.getTitle(), appendix.getContents());
    }
}
