package diplom.gorchanyuk.project.diplom.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_entry")
    @SequenceGenerator(name = "my_gen_entry", allocationSize = 1)
    private long id;

    @Column(name = "name")
    private  String name;

    @Column(columnDefinition = "VARCHAR NOT NULL")
    private String text;

    private Date dateAdded;

    private Date dateUpdate;

    @Column(name="slug")
    private String slug;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entryId")
    @Transient
    private List<Comment> comments;

    @ManyToOne()
    @JoinColumn(name = "topic_id")
    private Topic topicId;


    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private User ownerId;

}
