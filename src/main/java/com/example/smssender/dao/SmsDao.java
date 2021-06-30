package com.example.smssender.dao;

import com.example.smssender.models.Sms;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SmsDao {
    boolean add(Sms user);
    void addAll(List<Sms> smsList);
    List<Sms> getSmsFiltered(Map<String, Object> specification);
}
