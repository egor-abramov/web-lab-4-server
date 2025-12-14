package server.mapper;

import org.springframework.stereotype.Component;
import server.dto.UserDTO;
import server.dto.UserRole;
import server.dto.request.AuthRequest;
import server.entity.UserEntity;

@Component
public class UserMapper {
    public UserDTO toDTO(UserEntity user) {
        return new UserDTO(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                UserRole.valueOf(user.getRole())
        );
    }

    public UserDTO toDTO(AuthRequest request) {
        return new UserDTO(
                request.getLogin(),
                request.getPassword(),
                UserRole.USER
        );
    }

    public UserEntity toEntity(UserDTO user) {
        return new UserEntity(
                user.getLogin(),
                user.getPassword(),
                user.getRole().name()
        );
    }
}
