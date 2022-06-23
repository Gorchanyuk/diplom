package diplom.gorchanyuk.project.diplom.service;

import diplom.gorchanyuk.project.diplom.dto.AuthorityDTO;
import diplom.gorchanyuk.project.diplom.dto.UserDTO;
import diplom.gorchanyuk.project.diplom.entity.Authority;
import diplom.gorchanyuk.project.diplom.entity.DetailsUser;
import diplom.gorchanyuk.project.diplom.entity.User;
import diplom.gorchanyuk.project.diplom.exception.SuchEmailAlreadyExistException;
import diplom.gorchanyuk.project.diplom.exception.SuchUsernameAlreadyExistException;
import diplom.gorchanyuk.project.diplom.pagination.Paged;
import diplom.gorchanyuk.project.diplom.pagination.Paging;
import diplom.gorchanyuk.project.diplom.repository.AuthorityRepository;
import diplom.gorchanyuk.project.diplom.repository.DetailsUserRepository;
import diplom.gorchanyuk.project.diplom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final DetailsUserRepository detailsUserRepository;
    private final DetailsUserService detailsUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        DetailsUser detailsUser = detailsUserRepository.findByEmail(username);
        if (user == null && detailsUser != null) {
            user = userRepository.findByDetailsUser(detailsUser);
        }
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }

    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null) {
            throw new SuchUsernameAlreadyExistException("Пользователь с таким логином уже существует.");
        }
        DetailsUser detailsUser = detailsUserRepository.findByEmail(userDTO.getDetailsUser().getEmail());
        if (detailsUser != null) {
            throw new SuchEmailAlreadyExistException("Пользователь с такой электронной почтой уже зарегестрирован.");
        }
        userDTO.setDateJoined(new Date());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setAuthorities(new AuthorityDTO());
        user = modelMapper.map(userDTO, User.class);
        Set<Authority> authorities = new HashSet<>();
        for (Authority auth : user.getAuthorities()) {
            authorities.add(authorityRepository
                    .findByAuthority(auth.getAuthority())
                    .orElse(auth));
        }
        user.setAuthorities(authorities);
        userRepository.save(user);
    }

    public boolean delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void update(UserDTO userDTO) {
        detailsUserService.update(userDTO.getDetailsUser());
    }

    public void updateAvatar(User user) {
        detailsUserService.updateAvatar(user.getDetailsUser());
    }

    public void updateEmail(UserDTO userDTO) {
        detailsUserService.updateEmail(userDTO.getDetailsUser());
    }

    public void updatePassword(User user) {
        userRepository.save(user);
    }

    public void adminAuthority(UserDTO userDTO){
        User user = (User) loadUserByUsername(userDTO.getUsername());
        Authority authority = authorityRepository.findByAuthority("ROLE_ADMIN").orElseGet(null);
        Set<Authority> authorities = user.getAuthorities();
        if(userDTO.admin){
            authorities.add(authority);
        }else{
            authorities.remove(authority);
        }
        user.setAuthorities(authorities);
        userRepository.save(user);
    }


    public Paged<UserDTO> getPage(int pageNumber, int size) {
        //реализация с пагинацией и сортировкой
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.Direction.ASC, "dateJoined");
        Page<User> users = userRepository.findAll(request);
        Page<UserDTO> userPage = users.map(user -> {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            Set<AuthorityDTO> authorities = user.getAuthorities().stream()
                    .map(a -> modelMapper.map(a, AuthorityDTO.class))
                    .collect(Collectors.toSet());
            userDTO.setAuthorities(authorities);
            return userDTO;
        });

        return new Paged<>(userPage, Paging.of(userPage.getTotalPages(), pageNumber, size));
    }

}
