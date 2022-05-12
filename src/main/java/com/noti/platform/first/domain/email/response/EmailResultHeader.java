package com.noti.platform.first.domain.email.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailResultHeader {
    private Integer resultCode;
    private String resultMessage;
    private Boolean isSuccessful;
}
