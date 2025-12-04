package server.controller;

import org.springframework.security.core.userdetails.User;
import server.dto.request.point.PointRequest;
import server.dto.UserDTO;
import server.dto.response.PointResponse;
import server.service.point.PointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @GetMapping("")
    public ResponseEntity<List<PointResponse>> getPoints(@AuthenticationPrincipal User user) {
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getPassword());
        List<PointResponse> pointResponses = pointService.getByUser(userDTO);
        return ResponseEntity.ok(pointResponses);
    }

    @PostMapping("/process")
    public ResponseEntity<PointResponse> processPoint(@RequestBody @Valid PointRequest point, @AuthenticationPrincipal User user) {
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getPassword());
        PointResponse response = pointService.processPoint(point, userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clear(@AuthenticationPrincipal User user) {
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getPassword());
        pointService.deleteByUser(userDTO);
        return ResponseEntity.noContent().build();
    }
}
