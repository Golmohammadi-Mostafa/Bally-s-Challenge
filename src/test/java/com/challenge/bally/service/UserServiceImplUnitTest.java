package com.challenge.bally.service;

import com.challenge.bally.dto.UserRequestDto;
import com.challenge.bally.entity.User;
import com.challenge.bally.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository repository;
    @Mock
    ExclusionService exclusionService;

    @Test
    void findAll_getAllUsers_shouldReturnAllResources() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDay = LocalDate.parse("1989-12-02", formatter);
        List<User> users = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 20);

        User user1 = User.builder()
                .id(1L)
                .username("Mostafa")
                .password("Gol1234")
                .dateOfBirth(birthDay)
                .ssn(234567890)
                .build();
        User user2 = User.builder()
                .id(2L)
                .username("Mostafa2")
                .password("mGol1234")
                .dateOfBirth(birthDay)
                .ssn(345654345)
                .build();

        users.add(user1);
        users.add(user2);

        Page<User> all = new PageImpl<>(users, pageRequest, users.size());
        when(repository.findAll(pageRequest)).thenReturn(all);
        all = userService.getUsers(pageRequest);
        assertEquals(2, all.getTotalElements());
    }

    @Test
    void createUser_validateAndSave_returnNewUser() {
        UserRequestDto dto = new UserRequestDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDay = LocalDate.parse("1989-12-02", formatter);
        dto.setUsername("Mostafa");
        dto.setPassword("Gol123");
        dto.setDateOfBirth(birthDay);
        dto.setSsn("980023000");
        Optional<User> existUser = Optional.empty();
        User user = User.builder()
                .username("Mostafa")
                .password("Gol123")
                .dateOfBirth(birthDay)
                .ssn(980023000)
                .build();

        when(exclusionService.validate(dto.getDateOfBirth(), dto.getSsn())).thenReturn(Boolean.TRUE);
        when(repository.findByDateOfBirthAndSsn(dto.getDateOfBirth(), Integer.valueOf(dto.getSsn()))).thenReturn(existUser);
        when(repository.save(user)).thenReturn(user);
        user = userService.create(dto);

        assertEquals(user.getUsername(), dto.getUsername());

    }

}