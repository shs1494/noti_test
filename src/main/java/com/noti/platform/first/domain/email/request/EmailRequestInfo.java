package com.noti.platform.first.domain.email.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class EmailRequestInfo {
    private String senderAddress;
    private String title;
    private String body;
    private List<TemplateInfo> templateParameter;
    private List<ReceiveInfo> receiveMailAddr;
    private ReceiveInfo receiver;

//    public EmailRequestInfo() {
//        CommonInfo commonInfo = new CommonInfo();
//        ReceiveInfo receiveInfo = new ReceiveInfo();
//        setSenderAddress(commonInfo.getEmailAddress());
//        setTitle("test");
//        setBody("테스트");
//    }
}
