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
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public abstract class GeneralController {

    protected final ModelMapper modelMapper;
    protected final CourseService courseService;

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
        if (actualUser != null) {
            Set<CourseDTO> userCourses = courses.stream()
                    .flatMap(s -> s.getTopics().stream())
                    .flatMap(t -> t.getEntries().stream())
                    .filter(e -> e.getOwnerId().getId() == actualUser.getId())
                    .map(e->e.getTopicId().getCourseId())
                    .collect(Collectors.toSet());
            model.addAttribute("userCourses", userCourses);
        }

        model.addAttribute("courses", courses);
        model.addAttribute("courseParent", courseParent);
        model.addAttribute("user", user);
        return model;
    }

    //    Проверка является ли пользователь админом
    protected void isAdmin(Model model, User user) {
        boolean admin;
        if (user == null)
            admin = false;
        else
            admin = user.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("admin", admin);
    }

    //    Возвращае колллекцию CourseDTO из модели
    protected List<CourseDTO> getCourseDTOFromModel(Model model) {
        List<Object> objects = (List<Object>) (model.getAttribute("courses"));
        assert objects != null;
        return objects.stream()
                .map(o -> modelMapper.map(o, CourseDTO.class))
                .collect(Collectors.toList());
    }

//    protected void getMyEntries(Model model, User actualUser) {
//        List<EntryDTO> userEntries = entryService.findByOwner(actualUser.getId());
//        Set<TopicDTO> userTopics = userEntries.stream().map(EntryDTO::getTopicId).collect(Collectors.toSet());
//        Set<CourseDTO> userCourses = userTopics.stream().map(TopicDTO::getCourseId).collect(Collectors.toSet());
//        List<EntryDTO> entryDTOS =
//                model.addAttribute("")
//    }
}
