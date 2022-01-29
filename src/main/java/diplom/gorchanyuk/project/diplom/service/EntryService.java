package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.dto.EntryDTO;
import diplom.gorchanyuk.project.diplom.entity.Entry;
import diplom.gorchanyuk.project.diplom.repository.EntryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final ModelMapper modelMapper;

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


    @Transactional
    public void save( EntryDTO entryDTO) {
        Entry entry = modelMapper.map(entryDTO, Entry.class);
        if (entry.getId() == 0){
            entry.setDateUpdate(new Date());
        }else{
            entry.setDateAdded(new Date());
        }
        entryRepository.save(entry);
    }

    public boolean delete(long id){
        if (entryRepository.findById(id).isPresent()) {
            entryRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
