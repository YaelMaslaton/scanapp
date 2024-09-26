package com.mend.scanapp.service;

import com.mend.scanapp.DTO.ScanDTO;
import com.mend.scanapp.DTO.UserScanInfoResponseDTO;
import com.mend.scanapp.mapper.ScanAppMapper;
import com.mend.scanapp.model.AppUser;
import com.mend.scanapp.model.Scan;
import com.mend.scanapp.model.ScanStatus;
import com.mend.scanapp.repository.ScanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScanService {

    private final ScanRepository scanRepository;
    private final ScanAppMapper scanAppMapper;
    private final ExecuteScanService executeScanService;
    private final ValidationService validationService; // הוספת השירות החדש


    public void initiateScan(ScanDTO scanDTO) {
        validationService.validateUser(scanDTO.getUserId());
        validationService.validateCommit((long) scanDTO.getCommitId());
        validationService.validateCommitForUser(scanDTO);
        Scan scan = scanAppMapper.scanDTOToScan(scanDTO);
        scan.setStatus(ScanStatus.PENDING);
        scanRepository.save(scan);
        executeScanService.executeScan(scan);
    }

    public long countPendingScans() {
        return scanRepository.countByStatus(ScanStatus.PENDING);
    }

    public int getTotalIssuesByUser(Long userId) {
        validationService.validateUser(userId);
        return scanRepository.sumIssuesByUserId(userId);
    }

    public int getTotalIssues() {
        return scanRepository.sumIssues();
    }

    public UserScanInfoResponseDTO getUserScanByCommit(String commitId) {
        validationService.validateCommit(Long.valueOf(commitId));
        AppUser appUser = scanRepository.findUserByCommitId(Long.valueOf(commitId));
        Scan scan = scanRepository.findByCommitId(commitId);
        return createUserScanInfoResponseDTO(appUser, scan);
    }

    private UserScanInfoResponseDTO createUserScanInfoResponseDTO(AppUser appUser, Scan scan) {
        UserScanInfoResponseDTO responseDTO = new UserScanInfoResponseDTO();
        responseDTO.setUserType(appUser.getUserType());
        responseDTO.setUserId(appUser.getUserId());
        responseDTO.setUsername(appUser.getUsername());
        responseDTO.setEmail(appUser.getEmail());
        responseDTO.setScanId(scan.getScanId());
        responseDTO.setStatus(scan.getStatus());
        responseDTO.setCommitId(scan.getCommit().getCommitId());
        responseDTO.setValid(scan.isValid());
        return responseDTO;
    }
}
