package com.challenge.bally.service;


import com.challenge.bally.dto.UserRequestDto;
import com.challenge.bally.entity.User;
import com.challenge.bally.exception.CustomException;
import com.challenge.bally.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ExclusionService exclusionService;

    public UserServiceImpl(UserRepository repository, ExclusionService exclusionService) {
        this.repository = repository;
        this.exclusionService = exclusionService;

    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public User create(UserRequestDto dto) {
        validateUser(dto.getDateOfBirth(), Integer.valueOf(dto.getSsn()));
        User user = User.builder()
                .password(dto.getPassword())
                .username(dto.getUsername())
                .dateOfBirth(dto.getDateOfBirth())
                .ssn(Integer.valueOf(dto.getSsn()))
                .build();
        User savedUser = repository.save(user);
        log.info("User with dateOfBirth: {} and ssn: {} created successfully.", savedUser.getDateOfBirth(), savedUser.getSsn());
        return savedUser;
    }

    @Override
    public Optional<User> getUser(LocalDate dateOfBirth, Integer ssn) {
        log.info("Request to find user with dateOfBirth: {} and ssn: {}", dateOfBirth, ssn);
        return repository.findByDateOfBirthAndSsn(dateOfBirth, ssn);
    }

    private void validateUser(LocalDate dateOfBirth, Integer ssn) {
        if (Boolean.FALSE.equals(exclusionService.validate(dateOfBirth, String.valueOf(ssn)))) {
            log.error("User is in an exclusion list, ssn: {}", ssn);
            throw new CustomException("User is in an exclusion list", HttpStatus.NOT_ACCEPTABLE);
        }
        if (getUser(dateOfBirth, ssn).isPresent()) {
            log.error("User Already exist, ssn: {}", ssn);
            throw new CustomException("User Already exist", HttpStatus.CONFLICT);
        }
    }
}
