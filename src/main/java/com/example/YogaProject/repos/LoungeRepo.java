package com.example.YogaProject.repos;

import com.example.YogaProject.domain.Lounge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoungeRepo extends JpaRepository <Lounge, Long> {
    Lounge findByName(String name);
}
