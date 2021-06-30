package com.example.smssender.util;

import com.example.smssender.models.dto.SmsDto;
import lombok.Data;

import java.util.Set;

@Data
public class SmsWrapper {
    private Set<String> phones;
    private SmsDto smsDto;
}
