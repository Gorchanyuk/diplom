package diplom.gorchanyuk.project.diplom.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private long id;

    @NotBlank
    private String text;

    private Date dateAdded;

    private EntryDTO entryId;

    private UserDTO ownerId;

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", entryId=" + entryId +
                ", ownerId=" + ownerId +
                '}';
    }
}
