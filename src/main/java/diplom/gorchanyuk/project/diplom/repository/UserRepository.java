package diplom.gorchanyuk.project.diplom.repository;

import diplom.gorchanyuk.project.diplom.entity.DetailsUser;
import diplom.gorchanyuk.project.diplom.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    User findByDetailsUser(DetailsUser detailsUser);

//    Optional<User> findByDetailsUserSlug(String slug);

    Page<User> findAll(Pageable request);
}
