package com.example.YogaProject.repos;

import com.example.YogaProject.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository <Activity, Long> {

}
