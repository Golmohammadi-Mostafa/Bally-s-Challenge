package com.challenge.bally.service;

import com.challenge.bally.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ExclusionServiceImpl implements ExclusionService {
    private static final List<User> blackList;

    static {
        log.info("start to fill out users in black list state");
        blackList = new ArrayList<>();
        User user1 = User.builder()
                .password("Analytical3ngineRulz")
                .username("adaLovelace")
                .dateOfBirth(convertStringToLocalDate("1815-12-10"))
                .ssn(85385075)
                .build();
        User user2 = User.builder()
                .password("eniGmA123")
                .username("alanTuring")
                .dateOfBirth(convertStringToLocalDate("1912-06-23"))
                .ssn(123456789)
                .build();
        User user3 = User.builder()
                .password("zeD1")
                .username("konradZuse")
                .dateOfBirth(convertStringToLocalDate("1910-06-22"))
                .ssn(987654321)
                .build();
        blackList.add(user1);
        blackList.add(user2);
        blackList.add(user3);
    }

    @Override
    public boolean validate(LocalDate dateOfBirth, String ssn) {
        log.info("Request to validate  dateOfBirth: {} and ssn: {}", dateOfBirth, ssn);
        if (CollectionUtils.isEmpty(ExclusionServiceImpl.blackList)) {
            return true;
        }
        Optional<User> optionalUser = ExclusionServiceImpl.blackList.stream()
                .filter(user -> user.getDateOfBirth().equals(dateOfBirth) &&
                        String.valueOf(user.getSsn()).equals(ssn)).findAny();
        return optionalUser.isEmpty();
    }

    public static LocalDate convertStringToLocalDate(String date) {
        if (Objects.isNull(date)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
