package diplom.gorchanyuk.project.diplom.controller;

import diplom.gorchanyuk.project.diplom.dto.ChangePasswordDTO;
import diplom.gorchanyuk.project.diplom.dto.DetailsUserDTO;
import diplom.gorchanyuk.project.diplom.dto.ProfileMenuDTO;
import diplom.gorchanyuk.project.diplom.dto.UserDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.service.CourseService;
import diplom.gorchanyuk.project.diplom.service.ProfileMenuService;
import diplom.gorchanyuk.project.diplom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Valid
@Controller
public class ProfileController extends GeneralController {

    private final ProfileMenuService profileMenuService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public ProfileController(ModelMapper modelMapper, CourseService courseService, ProfileMenuService profileMenuService, PasswordEncoder passwordEncoder, UserService userService) {
        super(modelMapper, courseService);
        this.profileMenuService = profileMenuService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("proger/profile")
    public String profile() {
        return "redirect:/proger/profile/edit";
    }

    @GetMapping("/proger/profile/{item}")
    public String menuProfile(Model baseModel, @AuthenticationPrincipal User actualUser,
                              @PathVariable("item") String item) {
        Model model = getBaseModel(baseModel, actualUser);
        List<ProfileMenuDTO> menus = profileMenuService.findAll();
        model.addAttribute("menus", menus);
        model.addAttribute("item", item);
//        атрибут для страницы безопасности
        model.addAttribute("changePassword", new ChangePasswordDTO());
        //        Отсев не правильных путей
        List<String> itemsMenu = menus.stream().map(menu -> menu.getSlug()).collect(Collectors.toList());
        if (!itemsMenu.contains(item)) {
            return "profile-edit";
        }

        return "profile-" + item;
    }

    @PostMapping("/proger/profile/edit")
    public String editProfile(@ModelAttribute("user") UserDTO user) {
        userService.update(user);
        return "redirect:/proger/profile/edit";
    }

    @GetMapping("/proger/profile/update-avatar")
    public String redirectUpdateAvatar() {
        return "update-avatar";
    }

    @PutMapping("/proger/profile/update-avatar")
    public String UpdateAvatar(@RequestParam("file") MultipartFile file,
                               @AuthenticationPrincipal User actualUser) {

        final String UPLOADED_FOLDER = "src\\main\\resources\\static\\images\\avatars\\";
        if (file.isEmpty()) {
            return "redirect:/proger/profile/update-avatar";
        }

        try {
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            Path path = Paths.get(UPLOADED_FOLDER + actualUser.getUsername() + fileExtension);
            Files.write(path, bytes);
//            if (actualUser.getDetailsUser().getAvatar().isEmpty()) {
                actualUser.getDetailsUser().setAvatar("images\\avatars\\" + actualUser.getUsername() + fileExtension);
                userService.updateAvatar(actualUser);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/proger/profile/edit";
    }

    @DeleteMapping("/proger/profile/update-avatar")
    public String DeleteAvatar(@AuthenticationPrincipal User actualUser) {

        actualUser.getDetailsUser().setAvatar("");
        userService.updateAvatar(actualUser);
        return "redirect:/proger/profile/edit";
    }

    @PutMapping("/proger/profile/security")
    public String updateEmail(@ModelAttribute("detailsUser") DetailsUserDTO detailsUserDTO,
                              @AuthenticationPrincipal User actualUser
    ) {

        UserDTO userDTO = new UserDTO();
        detailsUserDTO.setId(actualUser.getDetailsUser().getId());
        userDTO.setDetailsUser(detailsUserDTO);
        userService.updateEmail(userDTO);
        return "redirect:/proger/profile/edit";
    }

    @PatchMapping("/proger/profile/security")
    public String changePassword(@ModelAttribute("changePassword") ChangePasswordDTO changePassword,
                                 @AuthenticationPrincipal User actualUser,
                                 BindingResult bindingResult,
                                 Model model) {

//        Проверка соответствия введенного пароля и дальнейшая замена на новый
        String errorMessage = "";
        if (passwordEncoder.matches(changePassword.getCurrentpassword(), actualUser.getPassword()) &&
                changePassword.getNewpassword().equals(changePassword.getConfirmpassword())) {
            actualUser.setPassword(passwordEncoder.encode(changePassword.getNewpassword()));
            userService.updatePassword(actualUser);
            String title = "Смена пароля.";
            String message = "Пароль успешно изменен.";
            model.addAttribute("title", title);
            model.addAttribute("message", message);

            return "info";
        } else if (!changePassword.getNewpassword().equals(changePassword.getConfirmpassword())) {
            errorMessage = "Пароли не совпадают";
        } else if(!passwordEncoder.matches(changePassword.getCurrentpassword(), actualUser.getPassword())) {
            errorMessage = "Ввведен не верный пароль";
        } else {
            return "redirect:/proger/profile/security";
        }
        List<ProfileMenuDTO> menus = profileMenuService.findAll();
        model.addAttribute("menus", menus);
        model.addAttribute("item", "security");
        Model basemodel = getBaseModel(model, actualUser);
        basemodel.addAttribute("changePassword", changePassword);
        FieldError fieldError = new FieldError("changePassword", "confirmpassword", errorMessage);
        bindingResult.addError(fieldError);
        
        return "profile-security";
    }

    @DeleteMapping("/proger/profile/delete")
    public String DeleteProfile(@AuthenticationPrincipal User actualUser) {

        userService.delete(actualUser.getId());
        return "redirect:/proger/logout";
    }

    @GetMapping("/proger/profile/menu_entry")
    public String createEntry(){
        return "redirect:/proger/menu_entry";
    }

}
