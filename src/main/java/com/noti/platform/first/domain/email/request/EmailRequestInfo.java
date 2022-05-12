package com.noti.platform.first.domain.email.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmailRequestInfo {
    private String templateId;
    private String parameterKey = "";
    private String parameterValue = "";
    private TemplateInfo templateParameter = new TemplateInfo();
    private List<ReceiveInfo> receiveMailAddr;
}

class ReceiveInfo {
    private String receiveMailAddress = "";
    private String receiveType = "";
}

class TemplateInfo {
    private String parameterKey = "";
    private String parameterValue = "";

}
