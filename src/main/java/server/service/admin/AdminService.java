package server.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.dto.UserDTO;
import server.dto.UserRole;
import server.dto.request.AuthRequest;
import server.exception.UserAlreadyExistsException;
import server.mapper.UserMapper;
import server.service.point.PointService;
import server.service.user.UserService;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;
    private final PointService pointService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void addAdmin(AuthRequest request) {
        if (userService.isUserExists(request.getLogin())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserDTO user = userMapper.toDTO(request);
        user.setPassword(encodedPassword);
        user.setRole(UserRole.ADMIN);

        try {
            userService.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("User already exists");
        }
    }

    public Map<String, Float> getStatistics(String minDateStr, String maxDateStr) {
        Map<String, Float> response = new HashMap<>();

        try {
            ZonedDateTime minDate;
            ZonedDateTime maxDate;

            if(minDateStr == null || minDateStr.isEmpty()) {
                minDate = ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
            } else {
                minDate = ZonedDateTime.parse(minDateStr, DateTimeFormatter.ISO_DATE_TIME).withZoneSameInstant(ZoneOffset.UTC);
            }

            if(maxDateStr == null || maxDateStr.isEmpty()) {
                maxDate = ZonedDateTime.of(3000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
            } else {
                maxDate = ZonedDateTime.parse(maxDateStr, DateTimeFormatter.ISO_DATE_TIME).withZoneSameInstant(ZoneOffset.UTC);
            }

            for (UserDTO user: userService.getAll()) {
                Float hitPercentage = pointService.getHitPercentageInRangeByUser(user, minDate, maxDate);
                response.put(user.getLogin(), hitPercentage);
            }

            return response;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Wrong data format");
        }
    }
}
