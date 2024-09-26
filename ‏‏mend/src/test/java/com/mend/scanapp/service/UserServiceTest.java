package com.mend.scanapp.service;

import com.mend.scanapp.DTO.UserActivityDTO;
import com.mend.scanapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void getTop10ActiveRegularUsers_ShouldReturnTop10ActiveUsers() {
        UserActivityDTO user1 = new UserActivityDTO();
        user1.setUsername("user1");

        UserActivityDTO user2 = new UserActivityDTO();
        user2.setUsername("user2");

        List<UserActivityDTO> expectedUsers = Arrays.asList(user1, user2);

        when(userRepository.findTopActiveUsers(PageRequest.of(0, 10))).thenReturn(expectedUsers);

        List<UserActivityDTO> actualUsers = userService.getTop10ActiveRegularUsers();

        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
    }
}
