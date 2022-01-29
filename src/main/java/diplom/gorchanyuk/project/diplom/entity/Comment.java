package diplom.gorchanyuk.project.diplom.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_comment")
    @SequenceGenerator(name = "my_gen_comment", allocationSize = 1)
    private long id;

    @Column(columnDefinition = "VARCHAR")
    private String text;

    @Column(name = "date_added")
    private Date dateAdded;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "entry_id")
    private Entry entryId;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private User ownerId;
}
