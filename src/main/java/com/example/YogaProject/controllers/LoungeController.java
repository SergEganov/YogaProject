package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.Lounge;
import com.example.YogaProject.service.ActivityTypeService;
import com.example.YogaProject.service.LoungeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;
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
    public String createLounge(Lounge lounge,
                               @RequestParam String start,
                               @RequestParam String finish){
        LoungeController.parseTime(lounge,start, finish);
        loungeService.save(lounge);
        return "redirect:/lounges";
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
    public String updateLounge(Lounge lounge,
                               @RequestParam String start,
                               @RequestParam String finish){
        LoungeController.parseTime(lounge,start, finish);
        loungeService.save(lounge);
        return "redirect:/lounges";
    }

    private static void parseTime(Lounge lounge, String start, String finish) {
        LocalTime startTime = LocalTime.parse(start);
        LocalTime finishTime = LocalTime.parse(finish);
        lounge.setStartTime(startTime);
        lounge.setFinishTime(finishTime);
    }
}
