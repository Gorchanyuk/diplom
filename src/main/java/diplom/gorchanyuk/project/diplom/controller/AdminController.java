package diplom.gorchanyuk.project.diplom.controller;


import diplom.gorchanyuk.project.diplom.dto.EntryDTO;
import diplom.gorchanyuk.project.diplom.dto.FeedbackDTO;
import diplom.gorchanyuk.project.diplom.dto.UserDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.pagination.Paged;
import diplom.gorchanyuk.project.diplom.service.CourseService;
import diplom.gorchanyuk.project.diplom.service.EntryService;
import diplom.gorchanyuk.project.diplom.service.FeedbackService;
import diplom.gorchanyuk.project.diplom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController extends GeneralController {

    private final EntryService entryService;
    private final UserService userService;
    private final FeedbackService feedbackService;


    public AdminController(ModelMapper modelMapper, CourseService courseService,
                           EntryService entryService, UserService userService, FeedbackService feedbackService) {
        super(modelMapper, courseService);
        this.entryService = entryService;
        this.userService = userService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/proger/admin")
    public String redirectAdminPage() {
        return "redirect:/proger/admin/entries";
    }

    @GetMapping("/proger/admin/{page}")
    public String adminPage(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                            Model model,
                            @AuthenticationPrincipal User actualUser,
                            @PathVariable("page") String page) {
        getBaseModel(model, actualUser);

        Paged<EntryDTO> entriesPublish = entryService.getPagePublish(pageNumber, size);
        Paged<EntryDTO> entriesOffer = entryService.getPageOffer(pageNumber, size);
        Paged<FeedbackDTO> feedbacks = feedbackService.getPage(pageNumber, size);
        Paged<UserDTO> users = userService.getPage(pageNumber, size);
        long newFeedbacks = feedbackService.getNoRead();

        model.addAttribute("page", page);
        model.addAttribute("entriesPublish", entriesPublish);
        model.addAttribute("entriesOffer", entriesOffer);
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("newFeedbacks", newFeedbacks);
        model.addAttribute("users", users);
        switch (page) {
            case "offers":
            case "entries":
                return "admin-entries-and-offers";
            case "users":
                return "admin-users";
            case "appeals":
                return "admin-appeals";
            default:
                return "redirect:/proger/admin/entries";
        }
    }

    @PostMapping("/proger/admin/users")
    public String toogleAdmin(@ModelAttribute("client") UserDTO client,
                            HttpServletRequest request) {

        userService.adminAuthority(client);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/proger/admin/appeals")
    public String toogleAppeal(@ModelAttribute("appeal") FeedbackDTO feedback,
                               HttpServletRequest request){
        feedbackService.readed(feedback);
        return "redirect:" + request.getHeader("referer");
    }
}
