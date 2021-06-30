package com.example.smssender.services;

import com.example.smssender.dao.TagDao;
import com.example.smssender.models.Sms;
import com.example.smssender.dao.SmsDao;
import com.example.smssender.models.dto.SmsDto;
import com.example.smssender.models.Tag;
import com.example.smssender.util.DtoConverter;
import com.example.smssender.util.SmsWrapper;
import com.example.smssender.util.WrapperOfSmsWrappers;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SmsServiceServiceImpl implements SmsService {

    private final SmsDao smsDao;
    private final TagDao tagDao;
    private final WebClient webClient;

    public SmsServiceServiceImpl(SmsDao smsDao, TagDao tagDao, WebClient webClient) {
        this.smsDao = smsDao;
        this.tagDao = tagDao;
        this.webClient = webClient;
    }

    @Override
    public boolean create(SmsDto smsDto) {
        Sms currentSms = DtoConverter.convertDtoToSms(smsDto);
        String response = null;
        try {
            response = webClient
                    .post()
                    .uri(String.join("", "/sms"))
                    .bodyValue(currentSms)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientException webClientException) {
            webClientException.printStackTrace();
        }

        if (response != null && response.contains("Success")) {
            currentSms.setIsSent(true);
            currentSms.setDateTime(LocalDateTime.now());
        } else {
            currentSms.setIsSent(false);
        }
        validateTagSet(currentSms);
        return smsDao.add(currentSms);
    }

    @Override
    public List<SmsDto> createAll(WrapperOfSmsWrappers smsWrapper) {
        List<SmsDto> failedSmsList = new ArrayList<>();
        for (SmsWrapper wrapper : smsWrapper.getWrapperList()) {
            SmsDto currentSms = wrapper.getSmsDto();
            for (String phone : wrapper.getPhones()) {
                currentSms.setPhone(phone);
                if (!create(currentSms)) {
                    failedSmsList.add(currentSms);
                }
            }
        }
        return failedSmsList;
    }

    @Override
    public List<Sms> getAllFiltered(Map<String, Object> specification) {
        return smsDao.getSmsFiltered(specification);
    }

    private void validateTagSet(Sms sms) {
        Set<Tag> tagSet = new HashSet<>();
        for (Tag tag : sms.getTags()) {
            Tag tag_from_db = tagDao.findTagByName(tag.getName());
            if (tag_from_db != null) {
                tagSet.add(tag_from_db);
            } else {
                tagSet.add(tag);
            }
        }
        sms.setTags(tagSet);
    }
}
