package server.mapper;

import org.springframework.stereotype.Component;
import server.dto.UserDTO;
import server.dto.request.AuthRequest;
import server.entity.UserEntity;

@Component
public class UserMapper {
    public UserDTO toDTO(UserEntity user) {
        return new UserDTO(
                user.getLogin(),
                user.getPassword()
        );
    }

    public UserDTO toDTO(AuthRequest request) {
        return new UserDTO(
                request.getLogin(),
                request.getPassword()
        );
    }

    public UserEntity toEntity(UserDTO user) {
        return new UserEntity(
                user.getLogin(),
                user.getPassword()
        );
    }
}
