package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.config.Transcriptor;
import diplom.gorchanyuk.project.diplom.dto.EntryDTO;
import diplom.gorchanyuk.project.diplom.entity.Entry;
import diplom.gorchanyuk.project.diplom.entity.Topic;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.pagination.Paged;
import diplom.gorchanyuk.project.diplom.pagination.Paging;
import diplom.gorchanyuk.project.diplom.repository.EntryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final ModelMapper modelMapper;
    private final TopicService topicService;
    private final EntityManager entityManager;

    public Set<EntryDTO> findAll() {
//        List<Entry> entries = entryRepository.findAll(Sort.by(Sort.Order.asc("dateAdded")));
        List<Entry> entries = entryRepository.findAll();
        return entries.stream()
                .map(entry -> modelMapper.map(entry, EntryDTO.class)).collect(Collectors.toSet());
    }

    public EntryDTO findById(long id) {
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(entry, EntryDTO.class);
    }

    public EntryDTO findBySlug(String slug) {
        Entry entry = entryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(entry, EntryDTO.class);
    }

    public Paged<EntryDTO> getPagePublish(int pageNumber, int size) {
        //реализация с пагинацией и сортировкой
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.Direction.ASC, "dateAdded");
        Page<Entry> entries= entryRepository.findAllByPublishIsTrue(request);
        Page<EntryDTO> entryPage = entries.map(entry -> modelMapper.map(entry, EntryDTO.class));

        return new Paged<>(entryPage, Paging.of(entryPage.getTotalPages(), pageNumber, size));
    }

    public Paged<EntryDTO> getPageOffer(int pageNumber, int size) {
        //реализация с пагинацией и сортировкой
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.Direction.ASC, "dateAdded");
        Page<Entry> entries= entryRepository.findAllByOfferIsTrue(request);
        Page<EntryDTO> entryPage = entries.map(entry -> modelMapper.map(entry, EntryDTO.class));

        return new Paged<>(entryPage, Paging.of(entryPage.getTotalPages(), pageNumber, size));
    }


    @Transactional
    public void save(EntryDTO entryDTO, User user) {

        entryDTO.getTopicId().getCourseId().setPublish(entryDTO.isPublish());
        Entry entry = modelMapper.map(entryDTO, Entry.class);
        Topic topic;

        if (entry.getId() == 0) {
            entry.setDateAdded(new Date());
            entry.setOwnerId(user);
        } else {
            Entry oldEntry = entityManager.getReference(Entry.class, entry.getId());
            entry.setDateAdded(oldEntry.getDateAdded());
            entry.setOwnerId(oldEntry.getOwnerId());
        }

        entry.setDateUpdate(new Date());
        entry.setSlug(Transcriptor.transcript(entryDTO.getName()));
        if (entry.getTopicId().getId() == 0) {
            topic = topicService.save(entryDTO.getTopicId());
            entry.setTopicId(topic);
        }else{
            topic = entityManager.getReference(Topic.class, entry.getTopicId().getId());
            entry.setTopicId(topic);
        }
        entryRepository.save(entry);
    }

    public void delete(Long id) {
        entryRepository.deleteById(id);
    }

    public List<EntryDTO> findByOwner(Long ownerId) {
        List<Entry> entries = entryRepository.findAllByOwnerId_Id(ownerId);
        return entries.stream()
                .map(entry -> modelMapper.map(entry, EntryDTO.class)).collect(Collectors.toList());
    }
}
