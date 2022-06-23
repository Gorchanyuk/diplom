package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.dto.DetailsUserDTO;
import diplom.gorchanyuk.project.diplom.entity.Complicacy;
import diplom.gorchanyuk.project.diplom.entity.DetailsUser;
import diplom.gorchanyuk.project.diplom.repository.DetailsUserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DetailsUserService {

    private final DetailsUserRepository detailsUserRepository;
    private final ModelMapper modelMapper;


    public void update(DetailsUserDTO detailsUserDTO){
        Optional<DetailsUser> optional = detailsUserRepository.findById(detailsUserDTO.getId());
        DetailsUser detailsUser;
        if(!optional.isPresent()){
            detailsUser = optional.orElse(modelMapper.map(detailsUserDTO, DetailsUser.class));
        }
        else{
            detailsUser = optional.get();
            detailsUser.setFirstName(detailsUserDTO.getFirstName());
            detailsUser.setSurename(detailsUserDTO.getSurename());
            detailsUser.setPhone(detailsUserDTO.getPhone());
            detailsUser.setComplicacy(modelMapper.map(detailsUserDTO.getComplicacy(), Complicacy.class));
        }

        try {
            detailsUser.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(detailsUserDTO.getBirthday()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        detailsUserRepository.save(detailsUser);


    }

    public void updateAvatar(DetailsUser detailsUser){
        detailsUserRepository.save(detailsUser);
    }

    public void updateEmail(DetailsUserDTO detailsUserDTO) {
        DetailsUser detailsUser = detailsUserRepository.findById(detailsUserDTO.getId()).get();
        detailsUser.setEmail(detailsUserDTO.getEmail());
        detailsUserRepository.save(detailsUser);
    }
}
