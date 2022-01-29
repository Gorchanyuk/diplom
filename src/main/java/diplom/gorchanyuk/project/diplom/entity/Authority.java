package diplom.gorchanyuk.project.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_authority")
    @SequenceGenerator(name = "my_gen_authority", allocationSize = 1)
    private long id;

    @Column(name = "authority", columnDefinition = "VARCHAR(100) UNIQUE NOT NULL")
    private String authority;

}
