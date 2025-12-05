package server.service.point;

import server.dto.UserDTO;
import server.dto.request.PointRequest;
import server.dto.response.PointResponse;
import server.entity.PointEntity;
import server.entity.UserEntity;
import server.exception.UserNotFoundException;
import server.exception.ValidationException;
import server.mapper.PointMapper;
import server.mapper.UserMapper;
import server.repository.PointRepository;
import server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final PointMapper pointMapper;
    private final UserMapper userMapper;
    private final HitChecker hitChecker;
    private final PointValidator pointValidator;

    public PointResponse processPoint(PointRequest point, UserDTO user) throws ValidationException {
        pointValidator.validate(point);
        boolean isHit = hitChecker.checkHit(point);

        UserEntity userEntity = userRepository.findByLogin(user.getLogin())
                .orElseThrow(() -> new UserNotFoundException("No such user with login: " + user.getLogin()));
        PointEntity pointEntity = pointMapper.toEntity(point, userEntity);
        pointEntity.setHit(isHit);
        pointEntity.setUtcTime(ZonedDateTime.now(Clock.systemUTC()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        pointEntity = pointRepository.save(pointEntity);

        return pointMapper.toResponse(pointEntity);
    }

    public List<PointResponse> getByUser(UserDTO user) {
        UserEntity userEntity = userRepository.findByLogin(user.getLogin())
                .orElseThrow(() -> new UserNotFoundException("No such user with login: " + user.getLogin()));

        List<PointEntity> pointEntities = pointRepository.findByUser(userEntity);
        return pointEntities.stream().map(pointMapper::toResponse).toList();
    }

    public void deleteByUser(UserDTO user) {
        UserEntity userEntity = userRepository.findByLogin(user.getLogin())
                .orElseThrow(() -> new UserNotFoundException("No such user with login: " + user.getLogin()));
        pointRepository.deleteByUser(userEntity);
    }
}
