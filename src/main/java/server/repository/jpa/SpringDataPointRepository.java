package server.repository.jpa;

import server.entity.PointEntity;
import server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface SpringDataPointRepository extends JpaRepository<PointEntity, Long> {
    void deleteByUserId(Long id);

    List<PointEntity> findByUserId(Long id);
}
