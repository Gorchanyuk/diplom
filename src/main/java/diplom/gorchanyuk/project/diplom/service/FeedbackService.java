package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.dto.FeedbackDTO;
import diplom.gorchanyuk.project.diplom.entity.DetailsUser;
import diplom.gorchanyuk.project.diplom.entity.Feedback;
import diplom.gorchanyuk.project.diplom.entity.ReasonFeedback;
import diplom.gorchanyuk.project.diplom.pagination.Paged;
import diplom.gorchanyuk.project.diplom.pagination.Paging;
import diplom.gorchanyuk.project.diplom.repository.DetailsUserRepository;
import diplom.gorchanyuk.project.diplom.repository.FeedbackRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;
    private final ReasonFeedbackService reasonFeedbackService;
    private final DetailsUserRepository detailsUserRepository;

    @Transactional
    public void save(FeedbackDTO feedbackDTO) {
        Feedback feedback = modelMapper.map(feedbackDTO, Feedback.class);
        ReasonFeedback reasonFeedback = reasonFeedbackService.findById(feedbackDTO.getReasonFeedback().getId());
        feedback.setReasonFeedback(reasonFeedback);
        DetailsUser detailsUser = feedback.getDetailsUser();
        if (detailsUser.getId() != 0) {
            detailsUser = detailsUserRepository.getById(feedback.getDetailsUser().getId());
        }
        feedback.setDetailsUser(detailsUser);
        feedback.setDateAdded(new Date());
        feedback.setRead(false);
        feedbackRepository.save(feedback);
    }

    public Paged<FeedbackDTO> getPage(int pageNumber, int size) {
        //реализация с пагинацией и сортировкой
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.Direction.ASC, "read", "dateAdded");
        Page<Feedback> feedbacks= feedbackRepository.findAll(request);
        Page<FeedbackDTO> feedbackPage = feedbacks.map(feedback -> modelMapper.map(feedback, FeedbackDTO.class));

        return new Paged<>(feedbackPage, Paging.of(feedbackPage.getTotalPages(), pageNumber, size));
    }

    public void readed(FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackRepository.findById(feedbackDTO.getId()).orElse(null);
        feedback.setRead(feedbackDTO.isRead());
        feedbackRepository.save(feedback);
    }

    public long getNoRead() {
        return feedbackRepository.findAllByReadIsFalse().size();
    }
}
