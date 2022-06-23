package diplom.gorchanyuk.project.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryDTO {

    private Long id;

    private  String name;

    @NotBlank
    private String text;

    private Date dateAdded;

    private Date dateUpdate;

    private TopicDTO topicId;

    private UserDTO ownerId;

    private String slug;

    private boolean publish;

    private boolean offer;

    @Override
    public String toString() {
        return "EntryDTO{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}

