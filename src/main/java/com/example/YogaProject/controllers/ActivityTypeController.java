package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.ActivityType;
import com.example.YogaProject.service.ActivityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ActivityTypeController {

    private final ActivityTypeService activityTypeService;

    @Autowired
    public ActivityTypeController(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    @GetMapping("/activityTypes")
    public String findAll(Model model){
        List<ActivityType> activityTypes = activityTypeService.findAll();
        model.addAttribute("activityTypes", activityTypes);
        return "activityTypes";
    }

    @GetMapping("/create-activityType")
    public String createActivityTypeForm(Model model){
        model.addAttribute(new ActivityType());
        return "create-activityType";
    }

    @PostMapping("/create-activityType")
    public String createActivityType(ActivityType activityType){
        activityTypeService.saveActivityType(activityType);
        return "redirect:/activityTypes";
    }

    @GetMapping("delete-activityType/{id}")
    public String deleteActivityType(@PathVariable("id") Long id){
        activityTypeService.deleteById(id);
        return "redirect:/activityTypes";
    }

    @GetMapping("/update-activityType/{id}")
    public String updateActivityTypeForm(@PathVariable("id") Long id, Model model){
        ActivityType activityType = activityTypeService.findById(id);
        model.addAttribute("activityType", activityType);
        return "update-activityType";
    }

    @PostMapping("/update-activityType")
    public String updateActivityType(ActivityType activityType){
        activityTypeService.saveActivityType(activityType);
        return "redirect:/activityTypes";
    }
}