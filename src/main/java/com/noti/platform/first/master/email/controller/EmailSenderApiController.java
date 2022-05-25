package com.noti.platform.first.master.email.controller;


import com.noti.platform.first.domain.email.request.RequestDTO;
import com.noti.platform.first.domain.email.response.EmailResultInfo;
import com.noti.platform.first.master.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Slf4j
@Controller
public class EmailSenderApiController {

    @Autowired
    private EmailService emailService;

//    @PostMapping("/totalEmail")
//    public String emailSendFromOpenApi(@RequestBody RequestDTO requestDTO) throws IOException {
//        EmailResultHeader response = emailService.emailSendFromOpenApi(requestDTO);
//        log.info(requestDTO.getMailType());
//        String result2 = String.format("[Result] %s (%s)", response.getResultMessage(), response.getResultCode());
//        log.info(emailService.emailRequestJsonInit(requestDTO));
//
//        return result2;
//    }

    @PostMapping("/totalEmail")
    public Object newEmailSendFromOpenApi(@RequestBody RequestDTO requestDTO, Model model) throws IOException {
        EmailResultInfo response = emailService.emailSendFromOpenApi(requestDTO);
        model.addAttribute("emailResultInfo",response);
        log.info(response.getHeader().getResultMessage());
        return "email/emailTable";

    }
}
