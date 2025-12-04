package server.repository.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import server.entity.UserEntity;
import server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.repository", havingValue = "jpa", matchIfMissing = true)
public class JpaUserRepository implements UserRepository {
    private final SpringDataUserRepository jpaRepository;

    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

    @Override
    public List<UserEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findByLogin(String login) {
        return jpaRepository.findByLogin(login);
    }

    @Override
    public UserEntity save(UserEntity user) {
        if (existsByLogin(user.getLogin())) {
            throw new RuntimeException("User with login: " + user.getLogin() + " already exist");
        }
        return jpaRepository.save(user);
    }

    @Override
    public boolean existsByLogin(String login) {
        return findByLogin(login).isPresent();
    }
}
