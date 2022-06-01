package com.noti.platform.first.domain.email.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmailRetrieveBody {
    private List<EmailRetrieveBodyInfo> data;
}
