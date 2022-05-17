package com.noti.platform.first.domain.email.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiveInfo {
    private String receiveMailAddress;
    private String receiveType;

    public ReceiveInfo(String receiveMailAddress) {
        this.receiveMailAddress = receiveMailAddress;
    }
    public ReceiveInfo(String receiveMailAddress, String receiveType) {
        this.receiveMailAddress = receiveMailAddress;
        this.receiveType = receiveType;
    }
}
