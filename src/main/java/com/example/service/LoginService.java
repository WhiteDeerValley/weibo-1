package com.example.service;

import com.example.Util.MyUtil;
import com.example.async.MailTask;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TaskExecutor taskExecutor;

    public Map<String,Object> login(User user)
    {
        Map<String,Object> map = new HashMap<>();
        List<User> users = userRepository.findAllByEmailAndPassWord(user.getEmail(),user.getPassWord());
        if (users.size() == 0)
        {
            System.out.println("用户名和密码错了哦");
            map.put("status","no");
            map.put("error","用户名或密码错误");
            return map;
        }
        if (users.size() > 1)
        {
            System.out.println("存在奇怪的事情了，居然有人账号和密码都一样！");
        }
        User user1 = users.get(0);
        map.put("status","yes");
        map.put("uid",user1.getId());
        map.put("nickName",user1.getNickName());
        map.put("email",user1.getEmail());


        return map;

    }

    public String logup(User user)
    {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
        Matcher m = p.matcher(user.getEmail());
        if(!m.matches()){
            return "邮箱格式有问题啊~";
        }

        //校验密码长度
        p = Pattern.compile("^\\w{6,20}$");
        m = p.matcher(user.getPassWord());
        if(!m.matches()){
            return "密码长度要在6到20之间~";
        }

        //检查密码
        if(!user.getPassWord().equals(user.getPassWordAgain())){
            return "两次密码输入不一致~";
        }

        //检查邮箱是否被注册
        int emailCount = userRepository.countByEmail(user.getEmail());
        if(emailCount>0){
            return "该邮箱已被注册~";
        }

        //构造user，设置未激活
        user.setActived(0);
        String activateCode = MyUtil.createActivateCode();
        user.setActivateCode(activateCode);
        user.setJoinTime(MyUtil.formatDate(new Date()));
        user.setNickName("DF"+new Random().nextInt(10000)+"号");

        //发送邮件
        taskExecutor.execute(new MailTask(activateCode,user.getEmail(),javaMailSender,1));

        userRepository.save(user);
        return "ok";
    }
}
