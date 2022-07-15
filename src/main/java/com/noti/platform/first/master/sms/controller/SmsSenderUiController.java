package com.noti.platform.first.master.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SmsSenderUiController {

    @GetMapping({"/sms"})
    public String smsSenderUi() { return "sms/sms"; }
}