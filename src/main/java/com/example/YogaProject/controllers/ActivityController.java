package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.Activity;
import com.example.YogaProject.service.ActivityService;
import com.example.YogaProject.service.ActivityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityTypeService activityTypeService;

    @Autowired
    public ActivityController(ActivityService activityService, ActivityTypeService activityTypeService) {
        this.activityService = activityService;
        this.activityTypeService = activityTypeService;
    }

    @GetMapping("/activities")
    public String findAll(Model model) {
        List<Activity> activities = activityService.findAll();
        model.addAttribute("activities", activities);
        return "activities-list";
    }

    @GetMapping("/create-activity")
    public String createActivityForm(Model model){
        model.addAttribute("activityTypes", activityTypeService.findAll());
        model.addAttribute("activity", new Activity());
        return "create-activity";
    }

    @PostMapping("/create-activity")
    public String createActivity(Activity activity) {
        activityService.saveActivity(activity);
        return "redirect:/activities";
    }

    @GetMapping("/delete-activity/{id}")
    public String deleteActivity(@PathVariable("id") Long id) {
        activityService.deleteById(id);
        return "redirect:/activities";
    }

    @GetMapping("/update-activity/{id}")
    public String updateActivityForm(@PathVariable("id") Long id, Model model){
        Activity activity = activityService.findById(id);
        model.addAttribute("activity", activity);
        return "update-activity";
    }

    @PostMapping("/update-activity")
    public String updateActivity(Activity activity){
        activityService.saveActivity(activity);
        return "redirect:/activities";
    }
}
