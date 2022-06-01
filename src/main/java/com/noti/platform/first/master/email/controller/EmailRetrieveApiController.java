package com.noti.platform.first.master.email.controller;


import com.noti.platform.first.master.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class EmailRetrieveApiController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/retrieveEmail")
    public String emailRetrieveFromOpenApi(@RequestBody String requestId) throws IOException {
        String response = emailService.emailRetrieveFromOpenApi(requestId);
        return response;
    }
}
