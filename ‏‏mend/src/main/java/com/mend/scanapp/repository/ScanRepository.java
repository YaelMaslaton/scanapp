package com.mend.scanapp.repository;

import com.mend.scanapp.model.AppUser;
import com.mend.scanapp.model.Scan;
import com.mend.scanapp.model.ScanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends JpaRepository<Scan, Long> {

    long countByStatus(ScanStatus status);

    @Query("SELECT SUM(s.issues) FROM Scan s WHERE s.appUser.userId = :userId")
    int sumIssuesByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(s.issues) FROM Scan s")
    int sumIssues();

    @Query("SELECT s FROM Scan s JOIN s.commit c JOIN s.appUser u WHERE c.commitId = :commitId")
    Scan findByCommitId(@Param("commitId") String commitId);

    @Query("SELECT u FROM AppUser u JOIN u.scans s JOIN s.commit c WHERE c.commitId = :commitId")
    AppUser findUserByCommitId(@Param("commitId") Long commitId);
}
