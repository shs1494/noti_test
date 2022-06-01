package com.noti.platform.first.domain.email.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRetrieveInfo {
    private EmailResultHeader header;
    private EmailRetrieveBody body;
}
