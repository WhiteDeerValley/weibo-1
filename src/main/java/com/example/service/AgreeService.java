package com.example.service;

import com.example.entity.Agree;
import com.example.entity.Message;
import com.example.entity.User;
import com.example.repository.AgreeRepository;
import com.example.repository.MessageRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreeService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AgreeRepository agreeRepository;

    public void addAgree(Message message, User user){
        Agree agree = new Agree();
        agree.setUser(user);
        agree.setMessage(message);
        agreeRepository.save(agree);
    }

    public void delAgree(Message message,User user){
        agreeRepository.deleteByUserAndMessage(user,message);
    }



}
