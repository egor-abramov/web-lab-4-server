package server.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.role = 'ADMIN'")
    boolean isAdminExists();
}
