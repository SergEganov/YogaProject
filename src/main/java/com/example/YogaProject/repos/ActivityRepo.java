package com.example.YogaProject.repos;

import com.example.YogaProject.domain.Activity;
import com.example.YogaProject.domain.Lounge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ActivityRepo extends JpaRepository <Activity, Long> {
    List<Activity> findByLoungeAndDateOfActivityOrderByStartTimeAsc(Lounge lounge, LocalDate localDate);

}
