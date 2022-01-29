package diplom.gorchanyuk.project.diplom.controller;


import diplom.gorchanyuk.project.diplom.dto.CourseDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BaseController extends GeneralController{

    public BaseController(ModelMapper modelMapper, CourseService courseService) {
        super(modelMapper, courseService);
    }


    @GetMapping("/")
    public String plug() {
        return "redirect:/proger";
    }


    @GetMapping("/proger")
    public String startPage(Model baseModel, @AuthenticationPrincipal User actualUser) {

        Model model = getBaseModel(baseModel, actualUser);

        List<CourseDTO> courses = getCourseDTOFromModel(model);

        List<CourseDTO> popular = courses.stream()
                .filter(CourseDTO::isPopular)
                .collect(Collectors.toList());

        List<CourseDTO> recomended = courses.stream()
                .filter(CourseDTO::isRecomended)
                .collect(Collectors.toList());

        List<CourseDTO> trending = courses.stream()
                .filter(CourseDTO::isTrending)
                .collect(Collectors.toList());

        model.addAttribute("recomended", recomended);
        model.addAttribute("trending", trending);
        model.addAttribute("popular", popular);

        return "index";
    }

    @GetMapping("/proger/courses")
    public String allCourses(Model baseModel, @AuthenticationPrincipal User actualUser){
        Model model = getBaseModel(baseModel, actualUser);
        List<CourseDTO> courses = getCourseDTOFromModel(model);

//        Выбираю только те курсы которые не имеют дочерних курсов
        List<CourseDTO> coursesChild = courses.stream()
                .filter(course->course.getChildren().isEmpty())
                .collect(Collectors.toList());
        model.addAttribute("coursesChild", coursesChild);
        return "/courses";
    }

    @GetMapping("/proger/admin")
    public String adminPage(Model baseModel, @AuthenticationPrincipal User actualUser) {
        Model model = getBaseModel(baseModel, actualUser);

        return "index";
    }


}
