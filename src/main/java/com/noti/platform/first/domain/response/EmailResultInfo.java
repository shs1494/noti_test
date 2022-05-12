package com.noti.platform.first.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EmailResultInfo {
    private String requestId;
    private List<EmailResultBody> results;
}
