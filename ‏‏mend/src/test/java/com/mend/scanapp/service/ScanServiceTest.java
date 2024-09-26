package com.mend.scanapp.service;

import com.mend.scanapp.DTO.ScanDTO;
import com.mend.scanapp.DTO.UserScanInfoResponseDTO;
import com.mend.scanapp.mapper.ScanAppMapper;
import com.mend.scanapp.model.*;
import com.mend.scanapp.repository.CommitRepository;
import com.mend.scanapp.repository.ScanRepository;
import com.mend.scanapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScanServiceTest {

    @InjectMocks
    ScanService scanService;
    @Mock
    ScanRepository scanRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    CommitRepository commitRepository;
    @Mock
    private ScanAppMapper scanAppMapper;
    @Mock
    private ExecuteScanService executeScanService;
    @Mock
    private ValidationService validationService;

    @Test
    void initiateScan_ShouldSaveScanAndSendMessage() {
        Scan scan = new Scan();
        Commit commit = new Commit();
        commit.setBranch(new Branch());
        commit.getBranch().setRepository(new Repository());
        commit.getBranch().getRepository().setOrganization(new Organization());
        commit.getBranch().getRepository().getOrganization().setAppUser(new AppUser());
        commit.getBranch().getRepository().getOrganization().getAppUser().setUserId(1L);
        ScanDTO scanDTO = getScanDTO();
        when(scanAppMapper.scanDTOToScan(scanDTO)).thenReturn(scan);
        scanService.initiateScan(scanDTO);
        verify(scanRepository).save(scan);
        assertEquals(ScanStatus.PENDING, scan.getStatus());
    }

    private static ScanDTO getScanDTO() {
        ScanDTO scanDTO = new ScanDTO();
        scanDTO.setUserId(1L);
        scanDTO.setCommitId(1);
        return scanDTO;
    }

    @Test
   void countPendingScans_ShouldReturnCount() {
       when(scanRepository.countByStatus(ScanStatus.PENDING)).thenReturn(5L);
      long count = scanService.countPendingScans();
       assertEquals(5, count);
   }

   @Test
    void getTotalIssuesByUser_ShouldReturnTotalIssues() {
        when(scanRepository.sumIssuesByUserId(1L)).thenReturn(10);
        int totalIssues = scanService.getTotalIssuesByUser(1L);
        assertEquals(10, totalIssues);
    }

    @Test
    void getTotalIssues_ShouldReturnTotalIssues() {
        when(scanRepository.sumIssues()).thenReturn(100);
        int totalIssues = scanService.getTotalIssues();
        assertEquals(100, totalIssues);
    }

    @Test
    void getScansByCommit_ShouldReturnUserScanInfoResponseDTO() {
        AppUser appUser = new AppUser();
        appUser.setUserId(1L);
        appUser.setUsername("user1");
        appUser.setEmail("user1@example.com");

        Scan scan = new Scan();
        scan.setScanId(1L);
        scan.setStatus(ScanStatus.PENDING);
        scan.setValid(true);
        scan.setCommit(new Commit());
        scan.getCommit().setCommitId(1);

        when(scanRepository.findUserByCommitId(1L)).thenReturn(appUser);
        when(scanRepository.findByCommitId("1")).thenReturn(scan);

        UserScanInfoResponseDTO responseDTO = scanService.getUserScanByCommit("1");

        assertEquals(appUser.getUserId(), responseDTO.getUserId());
        assertEquals(appUser.getUsername(), responseDTO.getUsername());
        assertEquals(scan.getScanId(), responseDTO.getScanId());
        assertEquals(scan.getStatus(), responseDTO.getStatus());
        assertEquals(scan.getCommit().getCommitId(), responseDTO.getCommitId());
        assertTrue(responseDTO.isValid());
    }
}
