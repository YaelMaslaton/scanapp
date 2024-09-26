package com.mend.scanapp.controller;

import com.mend.scanapp.DTO.ScanDTO;
import com.mend.scanapp.DTO.UserActivityDTO;
import com.mend.scanapp.DTO.UserScanInfoResponseDTO;
import com.mend.scanapp.service.ScanService;
import com.mend.scanapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scans")
@RequiredArgsConstructor
public class ScanController {

    private final ScanService scanService;
    private final UserService userService;

    @Operation(summary = "Initiate a scan", description = "Starts a new scan with the provided details.")
    @ApiResponse(responseCode = "200", description = "Scan initiated successfully")
    @PostMapping("/initiate")
    public void initiateScan(@Valid @RequestBody ScanDTO scanDTO) {
        scanService.initiateScan(scanDTO);
    }

    @Operation(summary = "Count pending scans", description = "Returns the total number of pending scans for all users.")
    @ApiResponse(responseCode = "200", description = "Total number of pending scans")
    @GetMapping("/pending/count")
    public long getPendingScansCount() {
        return scanService.countPendingScans();
    }

    @Operation(summary = "Get total issues found", description = "Returns the total number of issues found across all scans.")
    @ApiResponse(responseCode = "200", description = "Total number of issues found")
    @GetMapping("/issues/total")
    public int getTotalIssues() {
        return scanService.getTotalIssues();
    }

    @Operation(summary = "Get total issues by user", description = "Returns the total number of issues found for a specific user.")
    @ApiResponse(responseCode = "200", description = "Total number of issues found for the user")
    @GetMapping("/users/{userId}/issues/total")
    public int getTotalIssuesByUser(@Parameter(description = "ID of the user") @PathVariable Long userId) {
        return scanService.getTotalIssuesByUser(userId);
    }

    @Operation(summary = "Get most active regular users", description = "Returns the top 10 most active regular users.")
    @ApiResponse(responseCode = "200", description = "List of top active regular users")
    @GetMapping("/active-regular-users")
    public ResponseEntity<List<UserActivityDTO>> getActiveRegularUsers() {
        List<UserActivityDTO> activeUsers = userService.getTop10ActiveRegularUsers();
        return ResponseEntity.ok(activeUsers);
    }

    @Operation(summary = "Get user scan by commit", description = "Returns user scan by specific commit ID.")
    @ApiResponse(responseCode = "200", description = "User scan by the commit")
    @GetMapping("/commit/{commitId}")
    public ResponseEntity<UserScanInfoResponseDTO> getUserScanByCommit(
            @Parameter(description = "ID of the commit") @PathVariable String commitId) {
        UserScanInfoResponseDTO userScanInfoResponseDTO = scanService.getUserScanByCommit(commitId);
        return ResponseEntity.ok(userScanInfoResponseDTO);
    }
}
