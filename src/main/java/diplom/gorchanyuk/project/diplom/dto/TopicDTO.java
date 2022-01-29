package diplom.gorchanyuk.project.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {

    private long id;

    @NotBlank
    private String nameTopic;

    private String avatar;

    private List<EntryDTO> entries;

    private CourseDTO courseId;

    private String slug;

}
