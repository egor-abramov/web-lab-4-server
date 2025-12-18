package server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import server.dto.request.AuthRequest;
import server.dto.response.AdminStatisticResponse;
import server.service.admin.AdminService;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminStatisticResponse> getStatistics(@RequestParam(required = false) String minDate,
                                                             @RequestParam(required = false) String maxDate) {
        AdminStatisticResponse response = adminService.getStatistics(minDate, maxDate);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addAdmin(@RequestBody @Valid AuthRequest request) {
        adminService.addAdmin(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Admin registered");
    }
}
