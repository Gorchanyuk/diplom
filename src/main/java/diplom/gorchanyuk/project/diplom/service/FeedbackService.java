package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.dto.FeedbackDTO;
import diplom.gorchanyuk.project.diplom.entity.DetailsUser;
import diplom.gorchanyuk.project.diplom.entity.Feedback;
import diplom.gorchanyuk.project.diplom.entity.ReasonFeedback;
import diplom.gorchanyuk.project.diplom.repository.DetailsUserRepository;
import diplom.gorchanyuk.project.diplom.repository.FeedbackRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        ReasonFeedback reasonFeedback = reasonFeedbackService.findById(feedbackDTO.getReasonFeedback());
        feedback.setReasonFeedback(reasonFeedback);
        DetailsUser detailsUser = feedback.getDetailsUser();
        if (detailsUser.getId() != 0) {
            detailsUser = detailsUserRepository.getById(feedback.getDetailsUser().getId());
        }
        feedback.setDetailsUser(detailsUser);
        feedbackRepository.save(feedback);
    }
}
