package com.mend.scanapp.service;

import com.mend.scanapp.DTO.ScanDTO;
import com.mend.scanapp.model.*;
import com.mend.scanapp.repository.CommitRepository;
import com.mend.scanapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidationServiceTest {

    private UserRepository userRepository;
    private CommitRepository commitRepository;
    private ValidationService validationService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        commitRepository = mock(CommitRepository.class);
        validationService = new ValidationService(userRepository, commitRepository);
    }

    @Test
    public void testValidateUser_UserExists() {
        Long userId = 1L;
        AppUser user = new AppUser();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        validationService.validateUser(userId);

        verify(userRepository).findById(userId);
    }

    @Test
    public void testValidateUser_UserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validationService.validateUser(userId);
        });

        String expectedMessage = "AppUser not found with ID " + userId;
        String actualMessage = exception.getMessage();

        assert(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testValidateCommit_CommitExists() {
        int commitId = 1;
        Commit commit = new Commit();
        commit.setCommitId(commitId);

        when(commitRepository.findById((long) commitId)).thenReturn(Optional.of(commit));

        validationService.validateCommit((long) commitId);

        verify(commitRepository).findById((long) commitId);
    }

    @Test
    public void testValidateCommit_CommitDoesNotExist() {
        Long commitId = 1L;

        when(commitRepository.findById(commitId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validationService.validateCommit(commitId);
        });

        String expectedMessage = "Commit not found with ID " + commitId;
        String actualMessage = exception.getMessage();

        assert(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testValidateCommitForUser_CommitIsValidForUser() {
        Long userId = 1L;
        int commitId = 1;

        Commit commit = new Commit();
        commit.setBranch(mockBranchWithUserId(userId)); // Mock a branch that contains the user

        when(commitRepository.findById((long) commitId)).thenReturn(Optional.of(commit));

        ScanDTO scanDTO = new ScanDTO();
        scanDTO.setUserId(userId);
        scanDTO.setCommitId(commitId);

        validationService.validateCommitForUser(scanDTO);
    }

    @Test
    public void testValidateCommitForUser_CommitIsNotValidForUser() {
        Long userId = 1L;
        int commitId = 1;

        Commit commit = new Commit();
        commit.setBranch(mockBranchWithUserId(2L)); // Mock a branch that contains a different user

        when(commitRepository.findById((long) commitId)).thenReturn(Optional.of(commit));

        ScanDTO scanDTO = new ScanDTO();
        scanDTO.setUserId(userId);
        scanDTO.setCommitId(commitId);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validationService.validateCommitForUser(scanDTO);
        });

        String expectedMessage = "Commit ID is not associated with the user";
        String actualMessage = exception.getMessage();

        assert(actualMessage.contains(expectedMessage));
    }

    private Branch mockBranchWithUserId(Long userId) {
        Branch branch = new Branch();
        Repository repository = new Repository();
        Organization organization = new Organization();
        AppUser appUser = new AppUser();
        appUser.setUserId(userId);

        organization.setAppUser(appUser);
        repository.setOrganization(organization);
        branch.setRepository(repository);

        return branch;
    }
}
