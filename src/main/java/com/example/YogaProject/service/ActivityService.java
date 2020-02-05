package com.example.YogaProject.service;

import com.example.YogaProject.domain.Activity;
import com.example.YogaProject.repos.ActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepo activityRepo;

    @Autowired
    public ActivityService(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    public Activity findById(Long id) {
        return activityRepo.getOne(id);
    }

    public List<Activity> findAll() {
        return activityRepo.findAll();
    }

    public Activity saveActivity(Activity activity) {
        return activityRepo.save(activity);
    }

    public void deleteById(Long id) {
        activityRepo.deleteById(id);
    }

    public boolean checkForSignUp(Activity activity) {
        LocalDateTime todayDateTime = LocalDateTime.now();
        LocalDateTime activityDateTime = activity.getStartDateTime();
        if (activityDateTime.minusHours(2).isBefore(todayDateTime)) {
            return false;
        }
        if(activity.getUsers().size() >= activity.getCapacity()) {
            return false;
        }
        return true;
    }

}
