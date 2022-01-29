package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.dto.ReasonFeedbackDTO;
import diplom.gorchanyuk.project.diplom.entity.ReasonFeedback;
import diplom.gorchanyuk.project.diplom.repository.ReasonFeedbackRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReasonFeedbackService {

    private final ReasonFeedbackRepository reasonFeedbackRepository;
    private final ModelMapper modelMapper;

    public List<ReasonFeedbackDTO> findAll(){
        List<ReasonFeedback> reasonFeedback = reasonFeedbackRepository.findAll(Sort.by(Sort.Order.asc("id")));
        return reasonFeedback.stream()
                .map(entry -> modelMapper.map(entry, ReasonFeedbackDTO.class)).collect(Collectors.toList());
    }

    public ReasonFeedback findById(Long id){
        return reasonFeedbackRepository.findById(id).orElseGet(()-> reasonFeedbackRepository.getById(1L));
    }
}
