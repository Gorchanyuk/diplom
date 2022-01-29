package diplom.gorchanyuk.project.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_topic")
    @SequenceGenerator(name = "my_gen_topic", allocationSize = 1)
    private long id;

    @Column(name = "name_topic", columnDefinition ="VARCHAR(255) NOT NULL")
    private String nameTopic;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "date_added")
    private Date dateAdded;

    @Column(name="slug")
    private String slug;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topicId")
    private List<Entry> entries;

    @ManyToOne()
    @JoinColumn(name = "course_id")
    private Course courseId;
}
