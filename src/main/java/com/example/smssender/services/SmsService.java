package com.example.smssender.services;

import com.example.smssender.models.Sms;
import com.example.smssender.models.dto.SmsDto;
import com.example.smssender.util.WrapperOfSmsWrappers;

import java.util.List;
import java.util.Map;

public interface SmsService {

    boolean create(SmsDto smsDto);

    List<SmsDto> createAll(WrapperOfSmsWrappers smsWrapper);

    List<Sms> getAllFiltered(Map<String, Object> specification);
}
