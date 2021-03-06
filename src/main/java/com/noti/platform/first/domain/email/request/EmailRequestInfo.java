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
    private List<ReceiveInfo> receiverList;
}
