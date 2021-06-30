package com.example.smssender.controllers;

import com.example.smssender.models.Sms;
import com.example.smssender.models.dto.SmsDto;
import com.example.smssender.services.SmsService;
import com.example.smssender.util.WrapperOfSmsWrappers;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SmsRestController {

    private final SmsService smsService;

    public SmsRestController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/sms")
    public ResponseEntity<String> addSms(@RequestBody SmsDto smsDto) {
        boolean isSaved = smsService.create(smsDto);
        return new ResponseEntity<>(isSaved ? "OK" : "Error",
                isSaved
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/sms_batch")
    public ResponseEntity<List<SmsDto>> addMultipleSms(@RequestBody WrapperOfSmsWrappers smsWrapper) {
        return new ResponseEntity<>(smsService.createAll(smsWrapper), HttpStatus.ACCEPTED);
    }

    @GetMapping("/sms")
    public ResponseEntity<List<Sms>> getSmsFiltered(@RequestParam(name = "date_from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                    @RequestParam(name = "date_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
                                                    @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                                    @RequestParam(name = "phone", required = false) String phone,
                                                    @RequestParam(name = "tags", required = false) String[] tags) {
        Map<String, Object> filterMap = new HashMap<>();
        validateAndPut(filterMap, "date_from", dateFrom);
        validateAndPut(filterMap, "date_to", dateTo);
        validateAndPut(filterMap, "date", date);
        validateAndPut(filterMap, "phone", phone);
        validateAndPut(filterMap, "tags", tags);
        return new ResponseEntity<>(smsService.getAllFiltered(filterMap), HttpStatus.OK);
    }

    private void validateAndPut(Map<String, Object> map, String name, Object object) {
        if (object != null) {
            map.put(name, object);
        }
    }
}
