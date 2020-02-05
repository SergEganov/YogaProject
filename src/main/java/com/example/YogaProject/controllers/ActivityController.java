package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.Activity;
import com.example.YogaProject.domain.User;
import com.example.YogaProject.service.ActivityService;
import com.example.YogaProject.service.ActivityTypeService;
import com.example.YogaProject.service.LoungeService;
import com.example.YogaProject.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityTypeService activityTypeService;
    private final LoungeService loungeService;
    private final UserService userService;

    @Autowired
    public ActivityController(ActivityService activityService, ActivityTypeService activityTypeService, LoungeService loungeService,
                              UserService userService) {
        this.activityService = activityService;
        this.activityTypeService = activityTypeService;
        this.loungeService = loungeService;
        this.userService = userService;
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

    @GetMapping("/sign-up/{id}")
    public String signUpForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        Activity activity = activityService.findById(id);
        model.addAttribute("activity", activity);
        if (!activityService.checkForSignUp(activity)) {
            redirectAttributes.addFlashAttribute("message", "Запись окончена/нет мест");
            return "redirect:/activities";
        }
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @PostMapping("/sign-up/{id}")
    public String signUp(@PathVariable ("id") Long id, User user) {
        Activity activity = activityService.findById(id);
        User userFromDb =  userService.findByEmail(user.getEmail());
        if(userFromDb == null) {
            userService.saveUser(user);
            activity.getUsers().add(userService.findByEmail(user.getEmail()));
        } else {
            activity.getUsers().add(userFromDb);
        }
        activityService.saveActivity(activity);
        return "redirect:/activities";
    }

    @GetMapping("/registered-users/{id}")
    public String checkRegisteredUsers(@PathVariable("id") Long id,
                                       Model model){
        Activity activity = activityService.findById(id);
        model.addAttribute("users", activity.getUsers());
        model.addAttribute("activity", activity);
        return "users-on-activity";
    }

    @GetMapping("/delete-user-from-activity/{activity_id}/{user_id}")
    public String deleteUserFromActivity(@PathVariable("activity_id") Long activity_id,
                                         @PathVariable("user_id") Long user_id) {
        Activity activity = activityService.findById(activity_id);
        activity.getUsers().remove(userService.findById(user_id));
        activityService.saveActivity(activity);
        return "redirect:/registered-users/" + activity_id;
    }

    private static void parseTime(Activity activity, String start, String finish) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(start, dtf);
        LocalDateTime dateTime1 = LocalDateTime.parse(finish,dtf);
        activity.setStartDateTime(dateTime);
        activity.setFinishDateTime(dateTime1);
    }
}
