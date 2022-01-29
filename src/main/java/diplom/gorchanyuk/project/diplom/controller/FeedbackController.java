package diplom.gorchanyuk.project.diplom.controller;

import diplom.gorchanyuk.project.diplom.dto.FeedbackDTO;
import diplom.gorchanyuk.project.diplom.dto.ReasonFeedbackDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.service.FeedbackService;
import diplom.gorchanyuk.project.diplom.service.ReasonFeedbackService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final ReasonFeedbackService reasonFeedbackService;
    private final ModelMapper modelMapper;

    @GetMapping("/proger/feedback")
    public String addFeedback(Model model,
                           @AuthenticationPrincipal User actualUser){
        FeedbackDTO feedback = new FeedbackDTO();
        if (actualUser != null) {
            feedback = modelMapper.map(actualUser, FeedbackDTO.class);
        }
        List<ReasonFeedbackDTO> reasonFeedbacks = reasonFeedbackService.findAll();

        model.addAttribute("feedback", feedback);
        model.addAttribute("reasonFeedbacks", reasonFeedbacks);
        return "feedback";
    }

    @PostMapping("/proger/feedback")
    public String saveFeedback(@ModelAttribute("feedback") FeedbackDTO feedback, Model model){
        feedbackService.save(feedback);
        String title = "Ваша заявка принята.";
        String message = "Мы обязательно с вами свяжемся.";
        model.addAttribute("title", title);
        model.addAttribute("message", message);
        return "info";
    }
}
