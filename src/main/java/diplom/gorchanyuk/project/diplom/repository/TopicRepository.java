package diplom.gorchanyuk.project.diplom.repository;

import diplom.gorchanyuk.project.diplom.entity.Course;
import diplom.gorchanyuk.project.diplom.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic,Long> {

    Optional<Topic> findBySlug(String slug);

    List<Topic> findAllByCourseId(Course courseId);
}
