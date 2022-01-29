package diplom.gorchanyuk.project.diplom.controller;

import diplom.gorchanyuk.project.diplom.dto.UserDTO;
import diplom.gorchanyuk.project.diplom.exception.SuchEmailAlreadyExistException;
import diplom.gorchanyuk.project.diplom.exception.SuchUsernameAlreadyExistException;
import diplom.gorchanyuk.project.diplom.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Valid
@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/proger/login")
    public String getLogin(@ModelAttribute("user") UserDTO user) {
        return "login";
    }

    @GetMapping("/proger/registration")
    public String addUser(@ModelAttribute("user") UserDTO user){
        return "registration";
    }

    @PostMapping("/proger/registration")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO user,
                           BindingResult bindingResult,
                           Model model){
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            userService.save(user);
        }catch (SuchUsernameAlreadyExistException e){
            FieldError fieldError = new FieldError("user", "username", e.getMessage());
            bindingResult.addError(fieldError);
            return "registration";
        }catch (SuchEmailAlreadyExistException e){
            FieldError fieldError = new FieldError("user", "detailsUser.email", e.getMessage());
            bindingResult.addError(fieldError);
            return "registration";
        }
//        UsernamePasswordAuthenticationFilter authenticationFilter
//                =new UsernamePasswordAuthenticationFilter();
//        authenticationFilter.setUsernameParameter(user.getUsername());
//        authenticationFilter.setPasswordParameter((user.getPassword()));
//        DaoAuthenticationProvider provider1 = new DaoAuthenticationProvider();
//        PreAuthenticatedAuthenticationProvider provider2 = new PreAuthenticatedAuthenticationProvider();
//        RunAsImplAuthenticationProvider provider3 = new RunAsImplAuthenticationProvider();
//        JaasAuthenticationProvider provider = new JaasAuthenticationProvider();
//        AbstractJaasAuthenticationProvider provider4 = new DefaultJaasAuthenticationProvider();
//        ProviderManager providerManager = new ProviderManager(provider);
//        UsernamePasswordAuthenticationToken authToken
//                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//        authenticationFilter.getAuthenticationManager().authenticate(authRequest);
//        providerManager.authenticate(authToken);
        String title = "Вы успешно зарегестрированны.";
        String message = "";
        model.addAttribute("title", title);
        model.addAttribute("message", message);
        return  "info";
    }

}
