package diplom.gorchanyuk.project.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailsUserDTO {

    private long id;

    private String firstName;

    private String surename;

//    @Pattern(regexp = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$",
//            message = "Поле заполненно не верно.")
    @Email(message = "Поле заполненно не верно.")
    private String email;

    private String avatar;

    private String birthday;

    private String phone;

    private ComplicacyDTO complicacy;
}
