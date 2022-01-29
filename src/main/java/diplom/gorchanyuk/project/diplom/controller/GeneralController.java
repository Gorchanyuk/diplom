package diplom.gorchanyuk.project.diplom.controller;

import diplom.gorchanyuk.project.diplom.dto.CourseDTO;
import diplom.gorchanyuk.project.diplom.dto.UserDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.service.CourseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public abstract class GeneralController {

    private final ModelMapper modelMapper;
    private final CourseService courseService;

    //    Создаю базовую модель и помещаю туда юзера и курсы
    protected Model getBaseModel(Model model, User actualUser) {
        UserDTO user = new UserDTO();
        if (actualUser != null) {
            user = modelMapper.map(actualUser, UserDTO.class);
        }

        List<CourseDTO> courses = courseService.findAll();
        List<CourseDTO> courseParent = courses.stream()
                .filter(course -> course.getParent() == null)
                .collect(Collectors.toList());

        model.addAttribute("courses", courses);
        model.addAttribute("courseParent", courseParent);
        model.addAttribute("user", user);
        return model;
    }

    //    Возвращае колллекцию CourseDTO из модели
    protected List<CourseDTO> getCourseDTOFromModel(Model model){
        List<Object> objects = (List<Object>) (model.getAttribute("courses"));
        assert objects != null;
        return objects.stream()
                .map(o -> modelMapper.map(o, CourseDTO.class))
                .collect(Collectors.toList());
    }
}
