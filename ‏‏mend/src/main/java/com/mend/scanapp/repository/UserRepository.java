package com.mend.scanapp.repository;

import com.mend.scanapp.DTO.UserActivityDTO;
import com.mend.scanapp.model.AppUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT new com.mend.scanapp.DTO.UserActivityDTO(u.username, COUNT(s)) " +
            "FROM AppUser u " +
            "LEFT JOIN u.scans s " +
            "WHERE u.userType = 'REGULAR' " +
            "GROUP BY u.userId " +
            "ORDER BY COUNT(s) DESC")
    List<UserActivityDTO> findTopActiveUsers(Pageable pageable);
}



