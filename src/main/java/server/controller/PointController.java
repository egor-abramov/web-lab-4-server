package server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import server.dto.request.PointRequest;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PointResponse>> getPoints(@AuthenticationPrincipal UserDTO user) {
        List<PointResponse> pointResponses = pointService.getByUser(user);
        return ResponseEntity.ok(pointResponses);
    }

    @PostMapping("/process")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PointResponse> processPoint(@RequestBody @Valid PointRequest point, @AuthenticationPrincipal UserDTO user) {
        PointResponse response = pointService.processPoint(point, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> clear(@AuthenticationPrincipal UserDTO user) {
        pointService.deleteByUser(user);
        return ResponseEntity.noContent().build();
    }
}
