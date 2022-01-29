package diplom.gorchanyuk.project.diplom.controller;


import diplom.gorchanyuk.project.diplom.dto.CourseDTO;
import diplom.gorchanyuk.project.diplom.dto.EntryDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.service.CourseService;
import diplom.gorchanyuk.project.diplom.service.EntryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EntryController  extends GeneralController{

    @Autowired
    private EntryService entryService;
    @Autowired
    private CourseService courseService;
    public EntryController(ModelMapper modelMapper, CourseService courseService) {
        super(modelMapper, courseService);
    }

    @GetMapping("/proger/courses/{course}/{entry}")
    public String getEntry(@PathVariable("entry") String slugEntry,
                           @PathVariable("course") String slugCousre,
                           Model baseModel,
                           @AuthenticationPrincipal User actualUser){

        Model model = getBaseModel(baseModel, actualUser);
        EntryDTO entry = entryService.findBySlug(slugEntry);
        CourseDTO course = courseService.findBySlug(slugCousre);
        model.addAttribute("entry", entry);
        model.addAttribute("course", course);

        return "entry";
    }
}
