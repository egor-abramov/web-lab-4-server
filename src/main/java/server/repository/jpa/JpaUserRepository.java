package server.repository.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import server.entity.UserEntity;
import server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.repository", havingValue = "jpa", matchIfMissing = true)
public class JpaUserRepository implements UserRepository {
    private final SpringDataUserRepository jpaRepository;

    @Override
    public Optional<UserEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByLogin(String login) {
        return jpaRepository.findByLogin(login);
    }

    @Override
    public void save(UserEntity user) {
        if (existsById(user.getId())) {
            throw new RuntimeException("User with login: " + user.getLogin() + " already exist");
        }
        jpaRepository.save(user);
    }

    @Override
    public boolean existsByLogin(String login) {
        return findByLogin(login).isPresent();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }
}
