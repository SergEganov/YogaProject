package com.example.YogaProject.service;

import com.example.YogaProject.domain.Activity;
import com.example.YogaProject.domain.Lounge;
import com.example.YogaProject.domain.User;
import com.example.YogaProject.repos.ActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
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
        return activityRepo.findById(id).orElse(null);
    }

    public List<Activity> findAll() {
        return activityRepo.findAll();
    }

    public void saveActivity(Activity activity) {
        activityRepo.save(activity);
    }

    public void deleteById(Long id) {
        activityRepo.deleteById(id);
    }

    public List<Activity> findByLoungeAndDateOfActivityOrderByStartTimeAsc(Lounge lounge, LocalDate dateOfActivity){
        return activityRepo.findByLoungeAndDateOfActivityOrderByStartTimeAsc(lounge, dateOfActivity);
    }

    public boolean checkTimeForSignUp(Activity activity) {
        LocalDateTime todayDateTime = LocalDateTime.now();
        LocalDateTime activityDateTime = activity.getStartDateTime();
        if (activityDateTime.plusMinutes(30).isBefore(todayDateTime)) {
            return false;
        }
        if (activity.getUsers().size() >= activity.getCapacity()) {
            return false;
        }
        return true;
    }

    public boolean checkIllegalActivityTime(Activity activity, BindingResult bindingResult){
        if(!checkStartAndFinishTime(activity,bindingResult)) {
            return bindingResult.hasErrors();
            // проверяем корректное время
        }

        if(!checkLoungeCapacity(activity, bindingResult)) {
            return bindingResult.hasErrors();
            // проверяем по размеру помещения
        }

        if (!checkLoungeWorkPeriod(activity,bindingResult)) {
            return bindingResult.hasErrors();
            // проверяем по рабочему времени
        }

        List<Activity> activities = findByLoungeAndDateOfActivityOrderByStartTimeAsc(activity.getLounge(), activity.getDateOfActivity());
        if (activity.getId() != null) {
            //если идет операция update - выталкиваем текущую дату из списка проверок
            Activity activityFromDb = findById(activity.getId());
            activities.remove(activityFromDb);
        }
        if (activities.isEmpty()) {
            return bindingResult.hasErrors();
        }
        for(Activity act: activities) {
            if (!((activity.getStartTime().isBefore(act.getStartTime()) && activity.getFinishTime().plusMinutes(29).isBefore(act.getStartTime())) ||
            activity.getStartTime().isAfter(act.getFinishTime().plusMinutes(29)))) {

                bindingResult.addError(new FieldError(
                        "activity",
                        "startTime",
                        "Check activity time - 30 minutes between activities: " + activity.getStartTime() + " " + activity.getFinishTime()
                                + ". We have the same time activity in schedule " + act.getName() + " " + act.getStartTime() + " " + act.getFinishTime()));
                return bindingResult.hasErrors();
            }
        }
        return bindingResult.hasErrors();
    }

    private boolean checkLoungeWorkPeriod(Activity activity, BindingResult bindingResult) {
        if (activity.getStartTime().isBefore(activity.getLounge().getStartTime()) ||
                activity.getStartTime().isAfter(activity.getLounge().getFinishTime())
        ) {
            bindingResult.addError(new FieldError(
                    "activity",
                    "startTime",
                    "Check the time of the event: from " + activity.getStartTime()
                            + " to " + activity.getFinishTime()
                            + ". The lounge is working from " + activity.getLounge().getStartTime() + " to "
                            + activity.getLounge().getFinishTime()));
            return false;
        }
        if (activity.getFinishTime().isBefore(activity.getLounge().getStartTime()) ||
                activity.getFinishTime().isAfter(activity.getLounge().getFinishTime())
        ) {
            bindingResult.addError(new FieldError(
                    "activity",
                    "finishTime",
                    "Check the time of the event: from " + activity.getStartTime()
                            + " to " + activity.getFinishTime()
                            + ". The lounge is working from " + activity.getLounge().getStartTime() + " to "
                            + activity.getLounge().getFinishTime()));
            return false;
        }
        return true;
    }

    private boolean checkLoungeCapacity(Activity activity, BindingResult bindingResult) {
        if (activity.getCapacity() > activity.getLounge().getCapacity()) {
            bindingResult.addError(new FieldError(
                    "activity",
                    "capacity",
                    "Check the capacity of the event: " + activity.getCapacity()
                            + ". The lounge max capacity is " + activity.getLounge().getCapacity()));
            return false;
        }
        return true;
    }

    private boolean checkStartAndFinishTime(Activity activity, BindingResult bindingResult){
        if (activity.getStartTime().isAfter(activity.getFinishTime())) {
            bindingResult.addError(new FieldError(
                    "activity",
                    "finishTime",
                    "Check the time of the event: from " + activity.getStartTime()
                            + " to " + activity.getFinishTime()
                            + ". the finish can't be before the start"));
            return false;
        }
        return true;
    }
}

