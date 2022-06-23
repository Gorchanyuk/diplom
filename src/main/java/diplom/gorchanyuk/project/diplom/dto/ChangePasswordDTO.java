package diplom.gorchanyuk.project.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

    private String currentpassword;

    private String newpassword;

    private String confirmpassword;
}
