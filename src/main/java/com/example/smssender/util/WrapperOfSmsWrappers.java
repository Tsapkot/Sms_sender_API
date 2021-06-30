package com.example.smssender.util;

import lombok.Data;

import java.util.List;

@Data
public class WrapperOfSmsWrappers {
    private List<SmsWrapper> wrapperList;
}
