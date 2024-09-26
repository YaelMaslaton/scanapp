package com.mend.scanapp.repository;

import com.mend.scanapp.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface RepositoryRepository  extends JpaRepository<Repository, Long> {
}
