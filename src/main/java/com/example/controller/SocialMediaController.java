package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    // public SocialMediaController(){
    //     ApplicationContext applicationContext = SpringApplication.run(SocialMediaController.class);
    //     accountService = applicationContext.getBean(AccountService.class);
    //     messageService = applicationContext.getBean(MessageService.class);
    // }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account temp = accountService.login(account);
        if (temp != null) {
            return new ResponseEntity<>(temp, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<Account> userRegistration(@RequestBody Account account){
        if(accountService.login(account) != null){
            return new ResponseEntity<>(HttpStatus.valueOf(409));
        }
        Account temp = accountService.userRegistration(account);
        if(temp != null){
            return new ResponseEntity<>(temp, HttpStatus.valueOf(200));
        }
        else{
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message){
        if(accountService.findByID(message.getPostedBy()) == null){
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }
        Message temp = messageService.createNewMessage(message);
        if(temp != null){
            return new ResponseEntity<>(temp, HttpStatus.valueOf(200));
        }
        else{
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.valueOf(200));
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("message_id") int message_id){
        return new ResponseEntity<>(messageService.getMessage(message_id), HttpStatus.valueOf(200));
    }

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageByID(@PathVariable("message_id") int message_id){
        Message temp = messageService.deleteMessage(message_id);
        if(temp == null){
            return new ResponseEntity<>(HttpStatus.valueOf(200));
        }
        else{
            return new ResponseEntity<>(1, HttpStatus.valueOf(200));
        }
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessageByID(@PathVariable("message_id") int message_id, @RequestBody Message message){
        Message temp = messageService.updateMessage(message, message_id);
        if(temp == null){
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }
        else{
            return new ResponseEntity<>(1, HttpStatus.valueOf(200));
        }
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccount(@PathVariable("account_id") int account_id){
        return new ResponseEntity<>(messageService.getMessageByAccount(account_id), HttpStatus.valueOf(200));
    }
}
