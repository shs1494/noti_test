package com.noti.platform.first.domain.email.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailResultInfo {
    private EmailResultHeader header;
    private EmailResultData body;
}
