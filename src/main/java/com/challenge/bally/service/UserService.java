package com.challenge.bally.service;

import com.challenge.bally.dto.UserRequestDto;
import com.challenge.bally.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface UserService {
    Page<User> getUsers(Pageable pageable);

    User create(UserRequestDto dto);

    Optional<User> getUser(LocalDate dateOfBirth, Integer ssn);
}
