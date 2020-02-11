package com.example.YogaProject.repos;

import com.example.YogaProject.domain.Role;
import com.example.YogaProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepo extends JpaRepository <User, Long> {
    User findByEmail(String email);
    User findByLastName(String lastName);
    Set<User> findByRolesContains(Role role);
}
