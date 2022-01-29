package diplom.gorchanyuk.project.diplom.controller;

import diplom.gorchanyuk.project.diplom.dto.ProfileMenuDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.service.CourseService;
import diplom.gorchanyuk.project.diplom.service.ProfileMenuService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Valid
@Controller
public class ProfileController  extends GeneralController{

    private final ProfileMenuService profileMenuService;

    public ProfileController(ModelMapper modelMapper, CourseService courseService, ProfileMenuService profileMenuService) {
        super(modelMapper, courseService);
        this.profileMenuService = profileMenuService;
    }

    @GetMapping("proger/profile")
    public String profile(){
        return "redirect:/proger/profile/edit";
    }

    @GetMapping("/proger/profile/{item}")
    public String menuProfile(Model baseModel, @AuthenticationPrincipal User actualUser,
                            @PathVariable("item") String item) {
        Model model = getBaseModel(baseModel, actualUser);
        List<ProfileMenuDTO> menus = profileMenuService.findAll();
        model.addAttribute("menus", menus);
        model.addAttribute("item", item);
        //        Отсев не правильных путей
        List<String> itemsMenu = menus.stream().map(menu->menu.getSlug()).collect(Collectors.toList());
        if(!itemsMenu.contains(item)) {
            System.out.println();
            return "profile-edit";}

        return "profile-" + item;
    }
}
