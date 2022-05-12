package com.noti.platform.first.master.email.controller;


import com.noti.platform.first.domain.email.request.RequestDTO;
import com.noti.platform.first.domain.email.response.EmailResultHeader;
import com.noti.platform.first.master.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
public class EmailSenderApiController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/totalEmail")
    public String emailSendFromOpenApi(@RequestBody RequestDTO requestDTO) throws IOException {
        EmailResultHeader response = emailService.emailSendFromOpenApi(requestDTO);
        log.info(requestDTO.getMailType());
        String result2 = String.format("[Result] %s (%s)", response.getResultMessage(), response.getResultCode());

        return result2;
    }
}
