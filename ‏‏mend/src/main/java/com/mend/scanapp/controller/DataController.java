package com.mend.scanapp.controller;

import com.mend.scanapp.model.*;
import com.mend.scanapp.repository.OrganizationRepository;
import com.mend.scanapp.repository.RepositoryRepository;
import com.mend.scanapp.repository.BranchRepository;
import com.mend.scanapp.repository.CommitRepository;
import com.mend.scanapp.repository.ScanRepository;
import com.mend.scanapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DataController {

    private final UserRepository appUserRepository;

    private final OrganizationRepository organizationRepository;

    private final RepositoryRepository repositoryRepository;

    private final BranchRepository branchRepository;

    private final CommitRepository commitRepository;

    private final ScanRepository scanRepository;

    @PostMapping("/populate")
    public ResponseEntity<String> populateData() {
        for (int i = 1; i <= 5; i++) {
            AppUser user = new AppUser();
            user.setUsername("User" + i);
            user.setUserType(i % 2 == 0 ? UserType.GOLD : UserType.REGULAR);
            user.setEmail("user" + i + "@example.com");
            appUserRepository.save(user);
        }

        for (int i = 1; i <= 5; i++) {
            Organization organization = new Organization();
            organization.setOrganizationName("Organization" + i);
            organization.setAppUser(appUserRepository.findById((long) i).orElse(null));
            organizationRepository.save(organization);
        }

        for (int i = 1; i <= 5; i++) {
            Repository repository = new Repository();
            repository.setRepositoryName("Repository" + i);
            repository.setDescription("Description for repository " + i);
            repository.setOrganization(organizationRepository.findById((long) i).orElse(null));
            repositoryRepository.save(repository);
        }

        for (int i = 1; i <= 5; i++) {
            Branch branch = new Branch();
            branch.setBranchName("Branch" + i);
            branch.setRepository(repositoryRepository.findById((long) i).orElse(null));
            branchRepository.save(branch);
        }

        for (int i = 1; i <= 5; i++) {
            Commit commit = new Commit();
            commit.setCommitMessage("Commit message for commit " + i);
            commit.setCommitDate(new Date());
            commit.setBranch(branchRepository.findById((long) i).orElse(null));
            commitRepository.save(commit);
        }

        for (int i = 1; i <= 5; i++) {
            Scan scan = new Scan();
            scan.setType(ScanType.SCA);
            scan.setStatus(ScanStatus.COMPLETED);
            scan.setIssues(0);
            scan.setValid(true);
            scan.setCommit(commitRepository.findById((long)i).orElse(null));
            scan.setAppUser(appUserRepository.findById((long) i).orElse(null));
            scanRepository.save(scan);
        }

        return ResponseEntity.ok("Data populated successfully");
    }
}
