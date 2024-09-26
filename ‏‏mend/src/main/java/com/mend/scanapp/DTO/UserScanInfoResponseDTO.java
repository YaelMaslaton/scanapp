package com.mend.scanapp.DTO;

import com.mend.scanapp.model.*;
import lombok.Data;


@Data
public class UserScanInfoResponseDTO {
    private Long userId;
    private String username;
    private UserType userType;
    private String email;
    private Long scanId;
    private ScanType type;
    private ScanStatus status;
    private int commitId;
    private int issues;
    private boolean valid;
}
