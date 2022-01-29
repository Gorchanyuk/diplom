package diplom.gorchanyuk.project.diplom.repository;

import diplom.gorchanyuk.project.diplom.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    Optional<Entry> findBySlug(String slug);
}
