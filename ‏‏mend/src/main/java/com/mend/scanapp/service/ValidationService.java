package com.mend.scanapp.service;

import com.mend.scanapp.DTO.ScanDTO;
import com.mend.scanapp.model.AppUser;
import com.mend.scanapp.model.Commit;
import com.mend.scanapp.repository.CommitRepository;
import com.mend.scanapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ValidationService {

    private final UserRepository userRepository;
    private final CommitRepository commitRepository;

    public void validateUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found with ID " + userId));
    }

    public void validateCommit(Long commitId) {
        commitRepository.findById(commitId)
                .orElseThrow(() -> new IllegalArgumentException("Commit not found with ID " + commitId));
    }

    public void validateCommitForUser(ScanDTO scanDTO) {
        if (!isCommitIdValidForUser(scanDTO.getUserId(), scanDTO.getCommitId())) {
            throw new IllegalArgumentException("Commit ID is not associated with the user");
        }
    }

    private boolean isCommitIdValidForUser(Long userId, int commitId) {
        Optional<Commit> commitOpt = commitRepository.findById((long) commitId);
        return commitOpt.isPresent() && commitOpt.get().getBranch().getRepository().getOrganization().getAppUser().getUserId().equals(userId);
    }
}
