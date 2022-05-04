package com.noti.platform.first.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class EmailResultHeader {
    private Integer resultCode;
    private String resultMessage;
    private Boolean isSuccessful;
}
