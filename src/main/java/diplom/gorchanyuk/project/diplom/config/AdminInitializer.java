package diplom.gorchanyuk.project.diplom.config;

import diplom.gorchanyuk.project.diplom.dto.AuthorityDTO;
import diplom.gorchanyuk.project.diplom.dto.UserDTO;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.service.AuthorityService;
import diplom.gorchanyuk.project.diplom.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Configuration
public class AdminInitializer {

    private final AuthorityService authorityService;
    private final UserService userService;

    @PostConstruct
    public void postConstruct() {

        AuthorityDTO authority = authorityService.findByAuthority("ROLE_ADMIN");
        if(authority == null){
            authority = new AuthorityDTO();
            authority.setAuthority("ROLE_ADMIN");
            authorityService.save(authority);
        }

        try{
            User user = (User) userService.loadUserByUsername("admin");
        }catch (UsernameNotFoundException e){
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("admin");
            userDTO.setPassword("admin");
            userDTO.setAuthorities(authority);
            userService.save(userDTO);
        }

    }


}
