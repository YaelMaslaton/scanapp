package com.mend.scanapp.DTO;

import com.mend.scanapp.model.AppUser;
import com.mend.scanapp.model.Commit;
import com.mend.scanapp.model.ScanStatus;
import com.mend.scanapp.model.ScanType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScanDTO {

    @NotNull(message = "Type is required")
    private ScanType type;
    @NotNull(message = "Commit Id is required")
    private int commitId;
    @Min(value = 0, message = "Issues must be at least 0")
    private int issues;
    private boolean valid;
    @NotNull(message = "User Id is required")
    private Long userId;
}
