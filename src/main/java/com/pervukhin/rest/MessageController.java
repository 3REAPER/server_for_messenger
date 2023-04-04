package com.pervukhin.rest;

import com.pervukhin.domain.Message;
import com.pervukhin.rest.dto.MessageDto;
import com.pervukhin.service.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController() throws SQLException, ClassNotFoundException {
        this.messageService = new MessageServiceImpl();
    }

    @PostMapping("/message")
    public void createMessage(@RequestBody MessageDto messageDto){

        Message message = MessageDto.toDomainObject(messageDto);
        message.setTime(Message.getDateNow());

        messageService.insert(message, message.getChatId());
    }

    @PostMapping("/message/{id}")
    public void updateMessage(@RequestBody MessageDto messageDto){

        Message message = MessageDto.toDomainObject(messageDto);

        messageService.update(message);
    }

    @DeleteMapping("/message/{id}")
    public void delete(@PathVariable int id){
        messageService.delete(id);
    }

    @GetMapping("/message/{id}")
    public MessageDto getById(@PathVariable int id){
        return MessageDto.toDto(messageService.getById(id));
    }

    @GetMapping("/message/chat/{id}")
    public List<MessageDto> getAllByChatId(@PathVariable int id) {
        return MessageDto.toDto(messageService.getAllByChatId(id));
    }

    @GetMapping("/message")
    public List<MessageDto> getAll(){
        return MessageDto.toDto(messageService.getAll());
    }

    @GetMapping("/message/unread/{profileId}")
    public List<MessageDto> getUnread(@PathVariable int profileId){
        return MessageDto.toDto(messageService.getUnread(profileId));
    }
}
