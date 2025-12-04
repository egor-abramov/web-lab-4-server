package server.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import server.dto.UserDTO;
import server.entity.UserEntity;
import server.exception.UserNotFoundException;
import server.mapper.UserMapper;
import server.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public boolean isUserExists(UserDTO user) {
        return userRepository.existsByLogin(user.getLogin());
    }

    public boolean isUserExists(String login) {
        return userRepository.existsByLogin(login);
    }

    public UserDTO getByLogin(String login) {
        UserEntity userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("No such user with login: " + login));
        return userMapper.toDTO(userEntity);
    }

    public void save(UserDTO userDTO) {
        UserEntity userEntity = userMapper.toEntity(userDTO);
        userRepository.save(userEntity);
    }

    public void clear() {
        userRepository.deleteAll();
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDTO user = getByLogin(username);
        return User.withUsername(username)
                .password(user.getPassword())
                .build();
    }
}
