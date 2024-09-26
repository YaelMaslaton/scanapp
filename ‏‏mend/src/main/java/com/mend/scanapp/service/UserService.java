package com.mend.scanapp.service;

import com.mend.scanapp.DTO.UserActivityDTO;
import com.mend.scanapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserActivityDTO> getTop10ActiveRegularUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        return userRepository.findTopActiveUsers(pageable);
    }
}
