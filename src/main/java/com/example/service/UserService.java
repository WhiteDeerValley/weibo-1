package com.example.service;

import com.example.Util.MyConstant;
import com.example.entity.Message;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //根据用户id获取用户
    public User getUser(Long id){
        User user = new User();
        if (id == null)
        {
            id = MyConstant.PassengerId;
        }
        List<User> users = userRepository.findAllById(id);
        if (users.size() == 0)
            return user;
        else if(users.size() > 1)
        {
            System.out.println("用户Id不可能重叠哒");
        }
        user = users.get(0);
        return  user;
    }


}
