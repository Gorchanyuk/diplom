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

    private Long id;

    @NotBlank
    private String nameTopic;

    private List<EntryDTO> entries;

    private CourseDTO courseId;

    private String slug;


    public boolean isPublish(){
        //    Использую в Thymleaf для определения показывать тему или нет
        return this.entries.stream().anyMatch(EntryDTO::isPublish);
    }

    public boolean isUserHaveEntry(Long userId){
        //    Использую в Thymleaf для определения показывать тему или нет
        return this.entries.stream().anyMatch(e->e.getOwnerId().getId() == userId);
    }
}
