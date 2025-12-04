package server.repository.jpa;

import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import server.entity.PointEntity;
import server.entity.UserEntity;
import server.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.repository", havingValue = "jpa", matchIfMissing = true)
public class JpaPointRepository implements PointRepository {
    private final SpringDataPointRepository jpaRepository;

    @Override
    public PointEntity save(PointEntity point) {
        return jpaRepository.save(point);
    }

    @Override
    @Transactional
    public void deleteByUser(UserEntity user) {
        jpaRepository.deleteByUser(user);
    }

    @Override
    public List<PointEntity> findByUser(UserEntity user) {
        return jpaRepository.findByUser(user);
    }
}
