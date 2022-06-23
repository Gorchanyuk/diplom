package diplom.gorchanyuk.project.diplom.repository;

import diplom.gorchanyuk.project.diplom.entity.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    Optional<Entry> findBySlug(String slug);

   List<Entry> findAllByOwnerId_Id(Long id);

    Page<Entry> findAllByPublishIsTrue(Pageable request);

    Page<Entry> findAllByOfferIsTrue(Pageable request);
}
