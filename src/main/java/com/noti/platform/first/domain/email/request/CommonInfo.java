package com.noti.platform.first.domain.email.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CommonInfo {
    @Value("${email.appkey}")
    private String emailAppkey;

    @Value("${email.secretkey}")
    private String emailSecretkey;

    @Value("${email.address}")
    private String emailAddress;
}
