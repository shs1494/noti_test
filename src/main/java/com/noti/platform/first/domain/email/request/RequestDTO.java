package com.noti.platform.first.domain.email.request;

import lombok.Getter;

import java.util.List;

@Getter
public class RequestDTO {
    private String mailType;
    private List<String> receiveTypes;
}
