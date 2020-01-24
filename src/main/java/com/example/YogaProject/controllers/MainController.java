package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.Activity;
import com.example.YogaProject.repos.ActivityRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    private ActivityRepo activityRepo;

    public MainController(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    @GetMapping
    public String main(Model model) {
        Iterable<Activity> activities = activityRepo.findAll();
        model.addAttribute("activities", activities);
        return "main";
    }

    @PostMapping
    public String addActivity(@RequestParam String name, Model model) {

    }
}
