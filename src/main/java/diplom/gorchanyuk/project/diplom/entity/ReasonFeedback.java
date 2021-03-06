package diplom.gorchanyuk.project.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reason_feedback")
public class ReasonFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_reason_feedback")
    @SequenceGenerator(name = "my_gen_reason_feedback", allocationSize = 1)
    private long id;

    @Column(name = "reason", nullable = false)
    private String reason;
}
