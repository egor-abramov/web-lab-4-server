package server.repository.jpa;

import server.entity.PointEntity;
import server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface SpringDataPointRepository extends JpaRepository<PointEntity, Long> {
    void deleteByUser(UserEntity user);

    List<PointEntity> findByUser(UserEntity user);
}
