package diplom.gorchanyuk.project.diplom.repository;

import diplom.gorchanyuk.project.diplom.entity.DetailsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsUserRepository extends JpaRepository<DetailsUser, Long> {

    DetailsUser findByEmail(String email);
}
