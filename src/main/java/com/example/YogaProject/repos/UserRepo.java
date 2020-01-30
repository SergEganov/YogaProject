package com.example.YogaProject.repos;

import com.example.YogaProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository <User, Long> {
    List<User> findByLastName(String lastName);
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
}
