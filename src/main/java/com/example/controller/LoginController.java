package com.example.controller;

import com.example.entity.User;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/ajax/main")
public class LoginController {
    @Autowired
    LoginService loginService;
    @RequestMapping("/login")
    public String login(User user, Model model, HttpSession session)
    {
        System.out.println("进/ajax/main/login了");
        Map<String,Object> map = loginService.login(user);

        String status = (String)map.get("status");
        System.out.println(map);

        if ("yes".equals(map.get("status")))
        {
            session.setAttribute("uid",map.get("uid"));
            session.setAttribute("nickName",map.get("nickName"));
            session.setAttribute("email",map.get("email"));

            System.out.println("出/ajax/main/login了");
            return "success";
        }
        else
        {

            System.out.println("error");
            System.out.println("出/ajax/main/login了");
            return "no";
        }


    }

    @RequestMapping("/logup")
    public String logup(User user, Model model, HttpSession session)
    {
        System.out.println("进/ajax/main/logup了");
        String result = loginService.logup(user);
        if ("ok".equals(result))
        {
            model.addAttribute("info","系统已经向你的邮箱发送了一封邮件哦，验证后就可以登录啦~");
            System.out.println("系统已经向你的邮箱发送了一封邮件哦，验证后就可以登录啦~");
            System.out.println("出/ajax/main/logup了");
            return "success";
        }
        else {
            model.addAttribute("register","yes");
            model.addAttribute("email",user.getEmail());
            model.addAttribute("error",result);
            System.out.println("出/ajax/main/logup了");

            return "error";
        }
    }
}
