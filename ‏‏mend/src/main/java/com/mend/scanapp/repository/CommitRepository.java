package com.mend.scanapp.repository;

import com.mend.scanapp.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitRepository  extends JpaRepository<Commit, Long> {
}
