package server.repository;

import server.entity.PointEntity;
import server.entity.UserEntity;

import java.time.ZonedDateTime;
import java.util.List;

public interface PointRepository {
    PointEntity save(PointEntity point);

    void deleteByUserId(Long id);

    List<PointEntity> findByUserId(Long id);

    Long countByUserIdInRange(Long id, ZonedDateTime minDate, ZonedDateTime maxDate);

    Long countHitByUserIdInRange(Long id, ZonedDateTime minDate, ZonedDateTime maxDate);
}
