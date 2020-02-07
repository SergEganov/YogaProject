package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.Lounge;
import com.example.YogaProject.service.ActivityTypeService;
import com.example.YogaProject.service.LoungeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LoungeController {

    private final LoungeService loungeService;
    private final ActivityTypeService activityTypeService;

    @Autowired
    public LoungeController(LoungeService loungeService, ActivityTypeService activityTypeService) {
        this.loungeService = loungeService;
        this.activityTypeService = activityTypeService;
    }

    @GetMapping("/lounges")
    public String getUsers(Model model){
        List<Lounge> lounges = loungeService.findAll();
        model.addAttribute("lounges", lounges);
        return "lounges-list";
    }

    @GetMapping("/create-lounge")
    public String createLoungeForm(Model model){
        model.addAttribute("activityTypes", activityTypeService.findAll());
        model.addAttribute("lounge", new Lounge());
        return "create-lounge";
    }

    @PostMapping("/create-lounge")
    public String createLounge(@Valid Lounge lounge,
                               BindingResult bindingResult,
                               Model model){
        if (loungeService.createLoungeValidation(lounge, bindingResult)) {
            loungeService.save(lounge);
            return "redirect:/lounges";
        } else {
            model.addAttribute("activityTypes", activityTypeService.findAll());
            return "create-lounge";
        }

    }

    @GetMapping("delete-lounge/{id}")
    public String deleteLounge(@PathVariable("id") Long id) {
        loungeService.deleteById(id);
        return "redirect:/lounges";
    }

    @GetMapping("/update-lounge/{id}")
    public String updateLoungeForm(@PathVariable("id") Long id, Model model){
        Lounge lounge = loungeService.findById(id);
        model.addAttribute("activityTypes", activityTypeService.findAll());
        model.addAttribute("lounge", lounge);
        return "update-lounge";
    }

    @PostMapping("/update-lounge")
    public String updateLounge(@Valid Lounge lounge,
                               BindingResult bindingResult,
                               Model model){
        if (loungeService.updateLoungeValidation(lounge, bindingResult)) {
            loungeService.save(lounge);
            return "redirect:/lounges";
        } else {
            model.addAttribute("activityTypes", activityTypeService.findAll());
            return "update-lounge";
        }
    }
}
