package com.example.smssender.dao;

import com.example.smssender.models.Tag;

public interface TagDao {
    Tag findTagByName(String string);
}
