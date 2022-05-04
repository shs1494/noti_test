package com.noti.platform.first.controller;


import com.noti.platform.first.domain.response.EmailResultHeader;
import com.noti.platform.first.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

@RestController
public class CommonController {

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public String normalEmailSender(@RequestParam(value = "type") String typeData) throws IOException {
        System.out.println(typeData);
        EmailResultHeader response = emailService.emailSendFromOpenApi(typeData);
        String result = response.getResultMessage() + "(" + response.getResultCode() + ")";
        String result2 = String.format("[Result] %s (%s)", response.getResultMessage(), response.getResultCode());

        return result2;
    }

    @RequestMapping(value = "/authEmail", method = RequestMethod.GET)
    public String authEmailSender(@RequestParam(value = "type") String typeData) throws IOException {
        EmailResultHeader response = emailService.emailSendFromOpenApi(typeData);
        String result = response.getResultMessage() + "(" + response.getResultCode() + ")";
        String result2 = String.format("[Result] %s (%s)", response.getResultMessage(), response.getResultCode());

        return result2;
    }
}
