package com.mend.scanapp.service;
import com.mend.scanapp.model.Scan;
import com.mend.scanapp.model.ScanStatus;
import com.mend.scanapp.model.ScanType;
import com.mend.scanapp.repository.ScanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class ExecuteScanService {

    private final ScanRepository scanRepository;

    public void executeScan(Scan scan) {
        CompletableFuture.runAsync(() -> {
            try {
                long duration = getScanDuration(scan.getType());
                TimeUnit.SECONDS.sleep(duration);
                scan.setStatus(ScanStatus.COMPLETED);
                updateScanStatus(scan);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                setScanStatusToFailed(scan);
            } catch (Exception e) {
                setScanStatusToFailed(scan);
            }
        });
    }

    private void setScanStatusToFailed(Scan scan) {
        scan.setStatus(ScanStatus.FAILED);
        updateScanStatus(scan);
    }

    private long getScanDuration(ScanType scanType) {
        return switch (scanType) {
            case RENOVATE -> 30;
            case SCA -> 60;
            case SAST -> 90;
            default -> throw new IllegalArgumentException("Unknown scan type: " + scanType);
        };
    }

    private void updateScanStatus(Scan scan) {
        scanRepository.save(scan);
        log.info("Updated scan: {}, Status: {}", scan.getScanId(), scan.getStatus());
    }
}
