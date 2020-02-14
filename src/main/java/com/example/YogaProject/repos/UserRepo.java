package com.example.YogaProject.repos;

import com.example.YogaProject.domain.Role;
import com.example.YogaProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserRepo extends JpaRepository <User, Long> {
    User findByEmail(String email);
    Set<User> findByRolesContains(Role role);

    @Query(value = "SELECT EXISTS (SELECT NULL FROM usrs)", nativeQuery = true)
    boolean isUsersExists();
}

