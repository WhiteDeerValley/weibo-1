package com.example.controller;

import com.example.entity.Message;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageService messageService;

    @RequestMapping("/main")
    public String getMain(Model model, HttpServletRequest request, @RequestParam(value = "start",defaultValue = "0")int start, @RequestParam(value = "size",defaultValue = "5")int size)
    {


        //获取登陆信息
        HttpSession session = request.getSession();
        if (session.getAttribute("login")  == null)//总之不能让它为空，真正的值登陆的时候会添加的
        {
            request.setAttribute("userName","游客");
            model.addAttribute("login",0);
        }

        String method = (String)session.getAttribute("method");
        //获取排序信息
        if(method == null)
        {
            session.setAttribute("method","commentNum");
            method = "commentNum";
        }

        //获取首页微博表
        Page<Message> messages = messageService.listPostByMethod(start,size,method);

        //在session中插入当前页和页面大小
        session.setAttribute("curPage",start);
        session.setAttribute("size",size);
        //为html读取信息作准备
        model.addAttribute("messages",messages);
        //为后台登陆提交表单作准备
        User user = new User();
        model.addAttribute("User",user);

        return "main";
    }
}
