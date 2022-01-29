package diplom.gorchanyuk.project.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Это поле не может быть пустым")
    private String username;

    @Valid
    private DetailsUserDTO detailsUser;

    private Date dateJoined;

    @Size(min=6, message = "Пароль должен содержать минимум 6 символов.")
    private String password;

    private Date lastLogin;

    private Set<AuthorityDTO> authorities;

    public void setAuthorities(AuthorityDTO authority){
        if (authorities == null) authorities = new HashSet<>();
        this.authorities.add(authority);
    }

    public void setAuthorities(Set<AuthorityDTO> authorities){

        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                '}';
    }
}
