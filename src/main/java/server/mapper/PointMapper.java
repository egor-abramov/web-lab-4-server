package server.mapper;

import org.springframework.stereotype.Component;
import server.dto.request.PointRequest;
import server.dto.response.PointResponse;
import server.entity.PointEntity;
import server.entity.UserEntity;

@Component
public class PointMapper {
    public PointResponse toResponse(PointEntity point) {
        return new PointResponse(
                point.getId(),
                point.getX(),
                point.getY(),
                point.getR(),
                point.getUtcTime(),
                point.isHit()
        );
    }

    public PointEntity toEntity(PointRequest point, UserEntity user) {
        return new PointEntity(point.getX(), point.getY(), point.getR(), user);
    }
}
