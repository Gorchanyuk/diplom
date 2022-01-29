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
public class Complicacy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_gen_complicacy")
    @SequenceGenerator(name = "my_gen_complicacy", allocationSize = 1)
    private long id;

    @Column(name = "complicacy")
    private String complicacy;
}
