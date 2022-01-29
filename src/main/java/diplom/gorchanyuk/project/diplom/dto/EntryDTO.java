package diplom.gorchanyuk.project.diplom.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryDTO {

    private long id;

    private  String name;

    @NotBlank
    private String text;

    private Date dateAdded;

    private Date dateUpdate;

    private List<CommentDTO> comments;

    private TopicDTO topicId;

    private UserDTO ownerId;

    private String slug;

    @Override
    public String toString() {
        return "EntryDTO{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}

