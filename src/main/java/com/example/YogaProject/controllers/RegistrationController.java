package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.Role;
import com.example.YogaProject.domain.User;
import com.example.YogaProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String createUserForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping
    public String createUser(@Valid User user,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors() || !userService.createUserValidation(user, bindingResult)) {
            model.addAttribute("roles", Role.values());
            return "registration";
        }
        if(!user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult.addError(new FieldError(
                    "user",
                    "passwordConfirm",
                    "Check password and password confirm - it needs to be equal!"));
            return "registration";
        }
        user.getRoles().add(Role.ROLE_USER);
        if (!userService.isUserExists()) {
            user.getRoles().add(Role.ROLE_ADMIN);
        }
        userService.saveUser(user);
        return "redirect:/login";
    }
}
