package com.challenge.bally.rest;

import com.challenge.bally.dto.UserRequestDto;
import com.challenge.bally.dto.UserResponseDto;
import com.challenge.bally.dto.mapper.UserMapper;
import com.challenge.bally.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserResource {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserResource(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto dto) {
        return ResponseEntity.ok(userMapper.toDto(userService.create(dto)));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userMapper.toDto(userService.getUsers(pageable).getContent()));
    }
}
