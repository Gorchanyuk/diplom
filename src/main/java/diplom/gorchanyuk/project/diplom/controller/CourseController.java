package diplom.gorchanyuk.project.diplom.controller;

import diplom.gorchanyuk.project.diplom.dto.CourseDTO;
import diplom.gorchanyuk.project.diplom.dto.TopicDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CourseController extends GeneralController{

    @Autowired
    private CourseService courseService;

    public CourseController(ModelMapper modelMapper, CourseService courseService) {
        super(modelMapper, courseService);
    }

    @GetMapping("/proger/courses/{course}")
    public String getCourse(@PathVariable("course") String slugCourse,
                            Model model,
                            @AuthenticationPrincipal User actualUser){
        getBaseModel(model, actualUser);
        CourseDTO course = courseService.findBySlug(slugCourse);
        List<TopicDTO> topics = course.getTopics().stream().filter(TopicDTO::isPublish).collect(Collectors.toList());
        Boolean publish = true;
        model.addAttribute("public", publish);
        model.addAttribute("topics", topics);
        model.addAttribute("course", course);

        return "course";
    }

    @GetMapping("/proger/user_courses/{course}")
    public String getUserCourse(@PathVariable("course") String slugCourse,
                            Model model,
                            @AuthenticationPrincipal User actualUser){
        getBaseModel(model, actualUser);
        CourseDTO course = courseService.findBySlug(slugCourse);
        List<TopicDTO> topics = course.getTopics().stream()
                .filter(t->t.isUserHaveEntry(actualUser.getId()))
                .collect(Collectors.toList());
        Boolean publish = false;
        model.addAttribute("public", publish);
        model.addAttribute("topics", topics);
        model.addAttribute("course", course);

        return "course";
    }
}
