package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.Activity;
import com.example.YogaProject.service.ActivityService;
import com.example.YogaProject.service.ActivityTypeService;
import com.example.YogaProject.service.LoungeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityTypeService activityTypeService;
    private final LoungeService loungeService;

    @Autowired
    public ActivityController(ActivityService activityService, ActivityTypeService activityTypeService, LoungeService loungeService) {
        this.activityService = activityService;
        this.activityTypeService = activityTypeService;
        this.loungeService = loungeService;
    }

    @GetMapping("/activities")
    public String findAll(Model model) {
        List<Activity> activities = activityService.findAll();
        model.addAttribute("activities", activities);
        return "activities-list";
    }

    @GetMapping("/create-activity")
    public String createActivityForm(Model model){
        model.addAttribute("lounges", loungeService.findAll());
        model.addAttribute("activityTypes", activityTypeService.findAll());
        model.addAttribute("activity", new Activity());
        return "create-activity";
    }

    @PostMapping("/create-activity")
    public String createActivity(Activity activity,
                                 @RequestParam String start,
                                 @RequestParam String finish) {
        ActivityController.parseTime(activity, start, finish);
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
        model.addAttribute("lounges", loungeService.findAll());
        model.addAttribute("activityTypes", activityTypeService.findAll());
        Activity activity = activityService.findById(id);
        model.addAttribute("activity", activity);
        return "update-activity";
    }

    @PostMapping("/update-activity")
    public String updateActivity(Activity activity,
                                 @RequestParam String start,
                                 @RequestParam String finish) {
        ActivityController.parseTime(activity, start, finish);
        activityService.saveActivity(activity);
        return "redirect:/activities";
    }

    private static void parseTime(Activity activity, String start, String finish) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(start, dtf);
        LocalDateTime dateTime1 = LocalDateTime.parse(finish,dtf);
        activity.setStartDateTime(dateTime);
        activity.setFinishDateTime(dateTime1);
    }
}
