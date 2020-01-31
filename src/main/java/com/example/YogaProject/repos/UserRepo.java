package com.example.YogaProject.repos;

import com.example.YogaProject.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository <UserEntity, Long> {
    List<UserEntity> findByLastName(String lastName);
    UserEntity findByEmail(String email);
    UserEntity findByPhoneNumber(String phoneNumber);
}
