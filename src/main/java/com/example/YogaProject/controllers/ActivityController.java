package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.Activity;
import com.example.YogaProject.domain.Role;
import com.example.YogaProject.domain.User;
import com.example.YogaProject.service.ActivityService;
import com.example.YogaProject.service.ActivityTypeService;
import com.example.YogaProject.service.LoungeService;
import com.example.YogaProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/activities")
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

    @GetMapping
    public String findAll(Model model,
                          @AuthenticationPrincipal User user) {
        model.addAttribute("admin", userService.userIsAdmin(user));
        List<Activity> activities = activityService.findAll();
        model.addAttribute("activities", activities);
        return "activity/activities";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create-activity")
    public String createActivityForm(Model model){
        model.addAttribute("lounges", loungeService.findAll());
        model.addAttribute("activityTypes", activityTypeService.findAll());
        model.addAttribute("activity", new Activity());
        model.addAttribute("mentors", userService.findByRolesContains(Role.MENTOR));
        return "activity/create-activity";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create-activity")
    public String createActivity(@Valid Activity activity,
                                 BindingResult bindingResult,
                                 Model model) {
        if(bindingResult.hasErrors() || activityService.checkIllegalActivityTime(activity,bindingResult)) {
            model.addAttribute("activityTypes", activityTypeService.findAll());
            model.addAttribute("mentors", userService.findByRolesContains(Role.MENTOR));
            model.addAttribute("lounges", loungeService.findAll());
            return "activity/create-activity";
        }
            activityService.saveActivity(activity);
            return "redirect:/activities";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete-activity/{id}")
    public String deleteActivity(@PathVariable("id") Long id) {
        activityService.deleteById(id);
        return "redirect:/activities";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/update-activity/{id}")
    public String updateActivityForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("lounges", loungeService.findAll());
        model.addAttribute("activityTypes", activityTypeService.findAll());
        Activity activity = activityService.findById(id);
        model.addAttribute("activity", activity);
        model.addAttribute("mentors", userService.findByRolesContains(Role.MENTOR));
        return "activity/update-activity";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update-activity")
    public String updateActivity(@Valid Activity activity,
                                 BindingResult bindingResult,
                                 Model model) {
        if(bindingResult.hasErrors() || activityService.checkIllegalActivityTime(activity,bindingResult)) {
            model.addAttribute("lounges", loungeService.findAll());
            model.addAttribute("activityTypes", activityTypeService.findAll());
            model.addAttribute("mentors", userService.findByRolesContains(Role.MENTOR));
            return "activity/update-activity";
        }
            activityService.saveActivity(activity);
            return "redirect:/activities";
    }

    @GetMapping("/sign-up/{id}")
    public String signUpForm(@PathVariable("id") Long id, Model model){
        Activity activity = activityService.findById(id);
        if (!activityService.checkTimeForSignUp(activity)) {
            return "redirect:/activities";
        }
        model.addAttribute("id", id);
        model.addAttribute("user", new User());
        return "activity/sign-up";
    }

    @PostMapping("/sign-up/{id}")
    public String signUp(@PathVariable ("id") Long id,
                         @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "activity/sign-up";
        }
        Activity activity = activityService.findById(id);
        if (userService.checkUserAlreadySigned(user, activity, bindingResult)) {
            return "activity/sign-up";
        }
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/registered-users/{id}")
    public String checkRegisteredUsers(@PathVariable("id") Long id,
                                       Model model){
        Activity activity = activityService.findById(id);
        model.addAttribute("users", activity.getUsers());
        model.addAttribute("activity", activity);
        return "users/users-on-activity";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete-user-from-activity/{activity_id}/{user_id}")
    public String deleteUserFromActivity(@PathVariable("activity_id") Long activity_id,
                                         @PathVariable("user_id") Long user_id) {
        Activity activity = activityService.findById(activity_id);
        activity.getUsers().remove(userService.findById(user_id));
        activityService.saveActivity(activity);
        return "redirect:/registered-users/" + activity_id;
    }
}
