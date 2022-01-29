package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.dto.AuthorityDTO;
import diplom.gorchanyuk.project.diplom.entity.Authority;
import diplom.gorchanyuk.project.diplom.repository.AuthorityRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;

    public Set<AuthorityDTO> findAll() {
        List<Authority> authorities = authorityRepository.findAll();
        return authorities.stream()
                .map(authority -> modelMapper.map(authority, AuthorityDTO.class))
                .collect(Collectors.toSet());
    }

    @Transactional
    public boolean save(AuthorityDTO authority) {
        if (authority == null) return false;
        authorityRepository.save(modelMapper.map(authority, Authority.class));
        return true;
    }

    public AuthorityDTO findByAuthority(String role) {
        Authority authority = authorityRepository.findByAuthority(role)
                .orElse(null);
        return authority == null ? null : modelMapper.map(authority, AuthorityDTO.class);
    }
}
