package server.repository;

import server.entity.PointEntity;
import server.entity.UserEntity;

import java.util.List;

public interface PointRepository {
    PointEntity save(PointEntity point);

    void deleteByUser(UserEntity user);

    List<PointEntity> findByUser(UserEntity user);
}
