package com.noti.platform.first.domain.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
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

    public TemplateInfo() {

    }
}
