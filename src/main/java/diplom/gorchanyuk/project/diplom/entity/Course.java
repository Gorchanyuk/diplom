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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_course")
    @SequenceGenerator(name = "my_gen_course", allocationSize = 1)
    private long id;

    @Column(name = "name_course", columnDefinition ="VARCHAR(255) UNIQUE NOT NULL")
    private String nameCourse;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "date_added")
    private Date dateAdded;

    @Column(name = "description")
    private String description;

    @Column(name="slug")
    private String slug;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean publish;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private List<Topic> topics;

    public void removeTopic(Topic topic) {
        topics.removeIf(t -> t.equals(topic));
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Course> children;

    @ManyToOne
    private Course parent;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
//            CascadeType.PERSIST,
            CascadeType.REFRESH})
            @JoinColumn(name = "complicacy_id")
    private Complicacy complicacy;


    @Column(name = "popular", nullable = false)
    private boolean Popular;

    @Column(name = "trending", nullable = false)
    private boolean Trending;

    @Column(name = "recomended", nullable = false)
    private boolean Recomended;

}
