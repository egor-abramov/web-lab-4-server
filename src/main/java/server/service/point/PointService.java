package server.service.point;

import org.springframework.transaction.annotation.Transactional;
import server.dto.UserDTO;
import server.dto.request.PointRequest;
import server.dto.response.PointResponse;
import server.entity.PointEntity;
import server.entity.UserEntity;
import server.exception.UserNotFoundException;
import server.exception.ValidationException;
import server.mapper.PointMapper;
import server.repository.PointRepository;
import server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final PointMapper pointMapper;
    private final HitChecker hitChecker;
    private final PointValidator pointValidator;

    public PointResponse processPoint(PointRequest point, UserDTO user) throws ValidationException {
        pointValidator.validate(point);
        boolean isHit = hitChecker.checkHit(point);

        UserEntity userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("No such user"));
        PointEntity pointEntity = pointMapper.toEntity(point, userEntity);
        pointEntity.setHit(isHit);
        pointEntity.setUtcTime(ZonedDateTime.now(ZoneId.of("UTC")));

        pointEntity = pointRepository.save(pointEntity);

        return pointMapper.toResponse(pointEntity);
    }

    public List<PointResponse> getByUser(UserDTO user) {
        try{
            List<PointEntity> pointEntities = pointRepository.findByUserId(user.getId());
            return pointEntities.stream().map(pointMapper::toResponse).toList();
        } catch (Exception e) {
            throw new UserNotFoundException("No such user: " + user.getLogin());
        }
    }

    @Transactional
    public void deleteByUser(UserDTO user) {
        try {
            pointRepository.deleteByUserId(user.getId());
        } catch (Exception e) {
            throw new UserNotFoundException("No such user: " + user.getLogin());
        }
    }

    public Float getHitPercentageInRangeByUser(UserDTO user, ZonedDateTime minDate, ZonedDateTime maxDate) {
        try {
            float attemptsCount = (float) pointRepository.countByUserIdInRange(user.getId(), minDate, maxDate);
            float hitsCount = (float) pointRepository.countHitByUserIdInRange(user.getId(), minDate, maxDate);
            if(attemptsCount == 0) {
                return 0F;
            }
            return (float) Math.round(hitsCount / attemptsCount * 100) / 100F;
        } catch (Exception e) {
            throw new UserNotFoundException("No such user: " + user.getLogin());
        }
    }
}
