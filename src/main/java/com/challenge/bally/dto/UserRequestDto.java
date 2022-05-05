package com.challenge.bally.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must be alphanumeric with no spaces")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9]).{4,20}$",
            message = "Password must contain at least four characters," +
                    " at least one lower case character," +
                    " at least one upper case character, at least one number")
    private String password;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^(\\d{9})$", message = "Username must be alphanumeric with no spaces")
    private String ssn;
}
