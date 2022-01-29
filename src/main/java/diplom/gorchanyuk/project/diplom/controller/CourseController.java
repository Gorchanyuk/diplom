package diplom.gorchanyuk.project.diplom.controller;

import diplom.gorchanyuk.project.diplom.dto.CourseDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CourseController extends GeneralController{

    @Autowired
    private CourseService courseService;

    public CourseController(ModelMapper modelMapper, CourseService courseService) {
        super(modelMapper, courseService);
    }

    @GetMapping("/proger/courses/{course}")
    public String getCourse(@PathVariable("course") String slugCourse,
                            Model baseModel,
                            @AuthenticationPrincipal User actualUser){
        Model model = getBaseModel(baseModel, actualUser);
        CourseDTO course = courseService.findBySlug(slugCourse);
        model.addAttribute("course", course);

        return "course";
    }
}
