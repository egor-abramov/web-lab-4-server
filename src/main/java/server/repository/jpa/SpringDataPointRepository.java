package server.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

interface SpringDataPointRepository extends JpaRepository<PointEntity, Long> {
    void deleteByUserId(Long id);

    List<PointEntity> findByUserId(Long id);

    @Query("SELECT COUNT(p) FROM PointEntity p WHERE p.user.id = :id AND p.isHit = true AND p.utcTime >= :minDate AND p.utcTime <= :maxDate")
    Long countHitsByUserIdInRange(@Param("id") Long id, @Param("minDate") ZonedDateTime minDate, @Param("maxDate") ZonedDateTime maxDate);

    @Query("SELECT COUNT(p) FROM PointEntity p WHERE p.user.id = :id AND p.utcTime >= :minDate AND p.utcTime <= :maxDate")
    Long countByUserIdInRange(@Param("id") Long id, @Param("minDate") ZonedDateTime minDate, @Param("maxDate") ZonedDateTime maxDate);
}
