package com.pds.neviabookingreminder.controller;

import com.pds.neviabookingreminder.service.ReminderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminder")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping("/setDelay/{delay}")
    public void setDelay(@PathVariable Integer delay) {
        reminderService.setDelay(delay);
    }

}
