package com.example.YogaProject.service;

import com.example.YogaProject.domain.ActivityType;
import com.example.YogaProject.repos.ActivityTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class ActivityTypeService {

    private final ActivityTypeRepo activityTypeRepo;

    @Autowired
    public ActivityTypeService(ActivityTypeRepo activityTypeRepo) {
        this.activityTypeRepo = activityTypeRepo;
    }

    public List<ActivityType> findAll() {
        return activityTypeRepo.findAll();
    }

    public void saveActivityType(ActivityType activityType){
        activityTypeRepo.save(activityType);
    }

    public void deleteById(Long id) {
        activityTypeRepo.deleteById(id);
    }

    public ActivityType findById(Long id){
        return activityTypeRepo.getOne(id);
    }

    private Boolean checkActivityTypeExist(ActivityType activityType) {
        ActivityType activityTypeFromDb = activityTypeRepo.findByName(activityType.getName());
        return activityTypeFromDb != null;
    }

    public Boolean activityTypeValidation(ActivityType activityType, BindingResult bindingResult) {
        if (checkActivityTypeExist(activityType)) {
            bindingResult.addError(new FieldError(
                    "activityType",
                    "name",
                    "Activity type with name: " + activityType.getName() + " is exist!"));
        }
        return !bindingResult.hasErrors();
    }
}
