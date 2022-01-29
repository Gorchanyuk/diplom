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
@Table(name = "profile_menu")
public class ProfileMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_profile_menu")
    @SequenceGenerator(name = "my_gen_profile_menu", allocationSize = 1)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "svg_name")
    private String svgName;
}
