package diplom.gorchanyuk.project.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_feedback")
    @SequenceGenerator(name = "my_gen_feedback", allocationSize = 1)
    private long id;

    @ManyToOne(cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private DetailsUser detailsUser;

    @ManyToOne
    private ReasonFeedback reasonFeedback;

    @Column(name = "message", columnDefinition = "VARCHAR NOT NULL")
    private String message;

    @Column(name = "date_added")
    private Date dateAdded;

    private boolean read;
}
