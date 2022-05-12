package com.noti.platform.first.domain.email.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailResultBody {
    private String receiveMailAddr;
    private String receiveName;
    private String receiveType;
    private String resultMessage;
    private Integer resultCode;
}
