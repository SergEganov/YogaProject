package com.example.YogaProject.service;

import com.example.YogaProject.domain.User;
import com.example.YogaProject.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void isUsersExists() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void findByRolesContains() {
    }

    @Test
    void passwordConfirm() {
    }

    @Test
    void checkUserExist() {
        User user = new User();
        userService.saveUser(user);
        boolean isUserCreated =  userService.checkUserExist(user);
        Assertions.assertTrue(isUserCreated);
    }

    @Test
    void createUserValidation() {
    }

    @Test
    void updateUserValidation() {
    }

    @Test
    void checkUserAlreadySigned() {
    }
}