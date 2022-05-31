package com.samsung.dao;

import com.samsung.domain.Message;
import com.samsung.domain.Profile;

import java.util.List;

public interface MessageDao {
    void insert(Message message);

    void update(Message message);

    void delete(int id);

    Message getById(int id);

    List<Message> getAllByChatId(int chatId);

    List<Message> getAll();
}
