package com.challenge.bally.service;

import com.challenge.bally.Application;
import com.challenge.bally.dto.UserRequestDto;
import com.challenge.bally.entity.User;
import com.challenge.bally.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void deleteRecords() {
        userRepository.deleteAll();
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
        User user = userService.create(dto);

        assertEquals(user.getUsername(), dto.getUsername());

    }

    @Test
    void getUsers_saveTwoUsersAndFindAllUser_returnTwoUsers() {
        UserRequestDto dto = new UserRequestDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDay = LocalDate.parse("1989-12-02", formatter);
        dto.setUsername("Mostafa3");
        dto.setPassword("Gol1239");
        dto.setDateOfBirth(birthDay);
        dto.setSsn("980023088");
        userService.create(dto);

        UserRequestDto dto2 = new UserRequestDto();
        dto2.setUsername("Mostafa2");
        dto2.setPassword("Gol1232");
        dto2.setDateOfBirth(birthDay);
        dto2.setSsn("980023002");
        userService.create(dto2);

        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<User> users = userService.getUsers(pageRequest);
        assertEquals(users.getContent().size(), 2);
    }
}