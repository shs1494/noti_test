package com.noti.platform.first.domain.email.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateInfo {
    private String parameterKey;
    private Object parameterValue;
}
