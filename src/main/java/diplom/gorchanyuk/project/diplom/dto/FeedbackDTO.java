package diplom.gorchanyuk.project.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {

    private Long id;

    @Valid
    private DetailsUserDTO detailsUser;

    private ReasonFeedbackDTO reasonFeedback;

    private String message;

    private Date dateAdded;

    private boolean read;
}
