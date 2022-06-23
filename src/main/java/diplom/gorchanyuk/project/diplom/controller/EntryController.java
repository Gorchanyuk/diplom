package diplom.gorchanyuk.project.diplom.controller;


import diplom.gorchanyuk.project.diplom.dto.CourseDTO;
import diplom.gorchanyuk.project.diplom.dto.EntryDTO;
import diplom.gorchanyuk.project.diplom.dto.TopicDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.service.CourseService;
import diplom.gorchanyuk.project.diplom.service.EntryService;
import diplom.gorchanyuk.project.diplom.service.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EntryController extends GeneralController {

    @Autowired
    private EntryService entryService;

//    @Autowired
//    private CourseService courseService;

    @Autowired
    private TopicService topicService;

//    @Autowired
//    private ModelMapper modelMapper;

    public EntryController(ModelMapper modelMapper, CourseService courseService) {
        super(modelMapper, courseService);
    }

    @GetMapping("/proger/courses/{course}/{entry}")
    public String getEntry(@PathVariable("entry") String slugEntry,
                           @PathVariable("course") String slugCousre,
                           Model model,
                           @AuthenticationPrincipal User actualUser) {

        getBaseModel(model, actualUser);
        isAdmin(model, actualUser);
        EntryDTO entry = entryService.findBySlug(slugEntry);
        CourseDTO course = courseService.findBySlug(slugCousre);
        model.addAttribute("entry", entry);
        model.addAttribute("course", course);

        return "entry";
    }

    @GetMapping("/proger/menu_entry")
    public String createEntry(Model model,
                              @AuthenticationPrincipal User actualUser) {
        EntryDTO entry = new EntryDTO();
        getBaseModel(model, actualUser);
        isAdmin(model, actualUser);
        List<TopicDTO> topics = new ArrayList<>();

        model.addAttribute("entry", entry);
        model.addAttribute("topics", topics);

        return "create-entry";
    }

    @PutMapping("/proger/menu_entry")
    public String getTopicsForCourse(@ModelAttribute("entry") EntryDTO entry,
                       Model model,
                       @AuthenticationPrincipal User actualUser) {
        getBaseModel(model, actualUser);
        isAdmin(model, actualUser);
        CourseDTO course = entry.getTopicId().getCourseId();
        List<TopicDTO> topics = topicService.findByCourse(course);
        model.addAttribute("topics", topics);
        model.addAttribute("entry", entry);

        return "create-entry";
    }

    @PostMapping("/proger/menu_entry")
    public String saveEntry(@ModelAttribute("entry") EntryDTO entry,
                       Model model,
                       @AuthenticationPrincipal User actualUser) {
        entryService.save(entry, actualUser);
        String title = "Статья успешно сохранена.";
        model.addAttribute("title", title);
        return "info";
    }

    @PatchMapping("/proger/menu_entry/{entry}")
    public String editEntry(@PathVariable("entry") String slugEntry,
                            Model model,
                            @AuthenticationPrincipal User actualUser){

        EntryDTO entry = entryService.findBySlug(slugEntry);
        getBaseModel(model, actualUser);
        isAdmin(model, actualUser);
        CourseDTO course = entry.getTopicId().getCourseId();
        List<TopicDTO> topics = topicService.findByCourse(course);
        model.addAttribute("topics", topics);
        model.addAttribute("entry", entry);
        return "create-entry";
    }

    @DeleteMapping("/proger/menu_entry/{entry}")
    public String deleteEntry(@PathVariable("entry") String slugEntry,
                              Model model,
                              @AuthenticationPrincipal User actualUser){

        EntryDTO entry = entryService.findBySlug(slugEntry);
        if(entry.getOwnerId().getId() == actualUser.getId())
            entryService.delete(entry.getId());
        String title = "Статья удалена.";
        model.addAttribute("title", title);
        return "info";
    }


}
