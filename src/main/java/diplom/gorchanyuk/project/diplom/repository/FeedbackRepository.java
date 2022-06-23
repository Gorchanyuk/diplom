package diplom.gorchanyuk.project.diplom.repository;

import diplom.gorchanyuk.project.diplom.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Page<Feedback> findAll(Pageable request);

    List<Feedback> findAllByReadIsFalse();
}
