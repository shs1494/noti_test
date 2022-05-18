package com.noti.platform.first.common;

public enum ReceiverType {

    TO("MRT0"), CC("MRT1"), BCC("MRT2");

    private String value;

    ReceiverType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
