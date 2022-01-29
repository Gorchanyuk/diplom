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
@Table(name = "details_user")
public class DetailsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_details_for_user")
    @SequenceGenerator(name = "my_gen_details_for_user", allocationSize = 1)
    private long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "surename")
    private String surename;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "slug")
    private String slug;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "complicacy_id", columnDefinition = "BIGINT NOT NULL DEFAULT 1")
    private Complicacy complicacy;
}
