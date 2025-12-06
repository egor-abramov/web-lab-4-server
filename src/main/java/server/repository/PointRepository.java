package server.repository;

import server.entity.PointEntity;
import server.entity.UserEntity;

import java.util.List;

public interface PointRepository {
    PointEntity save(PointEntity point);

    void deleteByUserId(Long id);

    List<PointEntity> findByUserId(Long id);
}
