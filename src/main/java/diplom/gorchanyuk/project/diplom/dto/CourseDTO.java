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
public class CourseDTO {

    private long id;

    @NotBlank
    private String nameCourse;

    private String avatar;

    private String description;

    private String slug;

    private List<TopicDTO> topics;

    private CourseDTO parent;

    private List<CourseDTO> children;

    private ComplicacyDTO complicacy;

    private boolean Popular;

    private boolean Trending;

    private boolean Recomended;

    @Override
    public String toString() {
        return "CourseDTO{" +
                "nameCourse='" + nameCourse + '\'' +
                '}';
    }
}
