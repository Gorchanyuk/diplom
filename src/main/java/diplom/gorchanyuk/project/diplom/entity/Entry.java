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

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean publish;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean offer;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinColumn(name = "topic_id")
    private Topic topicId;

    @PreRemove
    private void removeEntry(){
        topicId.removeEntry(this);
    }


    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinColumn(name = "owner_id", nullable = false)
    private User ownerId;

}
