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

    public boolean isUserExists(String login) {
        return userRepository.existsByLogin(login);
    }

    public UserDTO getById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No such user with id: " + id));
        return userMapper.toDTO(userEntity);
    }

    public UserDTO getByLogin(String login) {
        UserEntity userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("No such user: " + login));
        return userMapper.toDTO(userEntity);
    }

    public void save(UserDTO userDTO) {
        UserEntity userEntity = userMapper.toEntity(userDTO);
        userRepository.save(userEntity);
    }

    public List<UserDTO> getAll() {
        return userRepository.getAll().stream().map(userMapper::toDTO).toList();
    }

    public boolean isAdminExists() {
        return userRepository.isAdminExists();
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        UserDTO user = getByLogin(login);
        return User.withUsername(login)
                .password(user.getPassword())
                .build();
    }
}
