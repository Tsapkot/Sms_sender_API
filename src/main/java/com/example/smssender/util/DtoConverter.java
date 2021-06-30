package com.example.smssender.util;

import com.example.smssender.models.Sms;
import com.example.smssender.models.dto.SmsDto;
import com.example.smssender.models.Tag;
import com.example.smssender.models.dto.TagDto;

import java.util.stream.Collectors;

public class DtoConverter {

    public static Sms convertDtoToSms(SmsDto smsDto) {
        return Sms.builder()
                .phone(smsDto.getPhone())
                .content(smsDto.getContent())
                .tags(smsDto.getTags().stream()
                        .map(DtoConverter::convertDtoToTag)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static SmsDto convertSmsToDto(Sms sms) {
        return SmsDto.builder()
                .phone(sms.getPhone())
                .content(sms.getContent())
                .tags(sms.getTags().stream()
                        .map(DtoConverter::convertTagToDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Tag convertDtoToTag(TagDto tagDto) {
        return Tag.builder()
                .name(tagDto.getName())
                .build();
    }

    public static TagDto convertTagToDto(Tag tag) {
        return TagDto.builder()
                .name(tag.getName())
                .build();
    }
}
