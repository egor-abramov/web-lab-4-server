package server.repository.jpa;

import server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByLogin(String login);
}
