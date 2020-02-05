package com.example.YogaProject.repos;

import com.example.YogaProject.domain.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityTypeRepo extends JpaRepository<ActivityType, Long> {
    ActivityType findByName(String name);
}
