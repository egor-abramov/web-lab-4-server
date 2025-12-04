package server.repository;

import server.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void deleteAll();

    List<UserEntity> findAll();

    Optional<UserEntity> findByLogin(String login);

    UserEntity save(UserEntity user);

    boolean existsByLogin(String username);
}
