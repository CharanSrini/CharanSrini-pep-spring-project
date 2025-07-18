package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    // @Autowired
    // public MessageService(MessageRepository messageRepository){
    //     this.messageRepository = messageRepository;
    // }

    public Message createNewMessage(Message newMessage){
        if(newMessage.getMessageText() != "" && newMessage.getMessageText().length() < 255){
            return messageRepository.save(newMessage);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessage(int message_id){
        return messageRepository.findById(message_id).orElse(null); 
    }

    public Message deleteMessage(int message_id){
        Message deleaten = messageRepository.findById(message_id).orElse(null);
        if(deleaten == null){
            return null;
        }
        messageRepository.deleteById(message_id);
        return deleaten;
    }

    public Message updateMessage(Message message, int message_id){
        Message update = messageRepository.findById(message_id).orElse(null);
        if(message.getMessageText() != "" && message.getMessageText().length() < 255 && update != null){
            update.setMessageText(message.getMessageText());
            return messageRepository.save(update);
        }
        return null;
    }

    public List<Message> getMessageByAccount(int account_id){
        return messageRepository.findByPostedBy(account_id);
    }
}
