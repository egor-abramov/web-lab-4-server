package server.repository;

import server.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findById(Long id);

    void save(UserEntity user);

    boolean existsByLogin(String userName);

    boolean existsById(Long id);
}
