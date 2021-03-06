package com.noti.platform.first.domain.email.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EmailResultBody {
    private String requestId;
    private List<EmailResultBodyInfo> results;
}
