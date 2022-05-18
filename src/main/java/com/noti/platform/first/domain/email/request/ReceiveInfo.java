package com.noti.platform.first.domain.email.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiveInfo {
    private String receiveMailAddr;
    private String receiveType;

    public ReceiveInfo(String receiveMailAddr) {
        this.receiveMailAddr = receiveMailAddr;
    }
    public ReceiveInfo(String receiveMailAddr, String receiveType) {
        this.receiveMailAddr = receiveMailAddr;
        this.receiveType = receiveType;
    }
}
