package com.noti.platform.first.master.email.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmailSenderUiController {

    @GetMapping({"/", "/email"})
    public String emailSenderUi() {
        return "email/email";
    }
}
