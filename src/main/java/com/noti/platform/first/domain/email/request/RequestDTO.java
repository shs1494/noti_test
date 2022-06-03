package com.noti.platform.first.domain.email.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDTO {
    private String mailType;
    private List<String> receiveTypes;
    private boolean eachType;
}
