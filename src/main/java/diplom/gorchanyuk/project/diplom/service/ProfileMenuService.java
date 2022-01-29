package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.dto.ProfileMenuDTO;
import diplom.gorchanyuk.project.diplom.entity.ProfileMenu;
import diplom.gorchanyuk.project.diplom.repository.ProfileMenuRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileMenuService {

    private final ProfileMenuRepository profileMenuRepository;
    private final ModelMapper modelMapper;

    public List<ProfileMenuDTO> findAll(){
        List<ProfileMenu> menus = profileMenuRepository.findAll(Sort.by(Sort.Order.asc("id")));
        return menus.stream()
                .map(menu->modelMapper.map(menu, ProfileMenuDTO.class))
                .collect(Collectors.toList());
    }
}
