package com.example.controller;

import com.example.Util.MyUtil;
import com.example.entity.Comment;
import com.example.entity.Message;
import com.example.entity.User;
import com.example.repository.CommentRepository;
import com.example.repository.MessageRepository;
import com.example.repository.UserRepository;
import com.example.service.MessageService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class RestMain {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/ajax/load/head")
    public String reloadhead(HttpSession session)
    {
        System.out.println("进入/ajax/load/head");
        String nickName = (String)session.getAttribute("nickName");
        System.out.println(nickName);
        System.out.println("出/ajax/load/head");
        return nickName;
    }

    @RequestMapping("/ajax/submitMessage")
    public String submitMessage(HttpSession session, HttpServletRequest request,@RequestParam("file") MultipartFile file, @RequestParam("content") String content) throws IOException {
        System.out.println("进入/ajaxsubmitMessage");

        //获取用户uid
        Long uid = (Long) session.getAttribute("uid");
        //未登录或者session过期了，反正是不给评论的
        if (uid == null)
        {
            System.out.println("login");
            System.out.println("进入/ajaxsubmitMessage");
            return "login";
        }

        //获取用户信息
        List<User> users = userRepository.findAllById(uid);
        //保存图片
        boolean b = MyUtil.uploadOneFile(request,file);

        if (b && users.size() > 0) {
            String messageUrl = (String) session.getAttribute("imgUrl");

            System.out.println(session.getAttribute("imgUrl"));
            Message message = new Message(users.get(0).getId(), users.get(0).getNickName(), content, messageUrl);

            messageRepository.save(message);
            return "success";
        }
        else
            return "error";


    }

    @RequestMapping("/ajax/makeComments")
    public String makeComment(HttpSession session, HttpServletRequest request, @RequestParam("content") String content) throws IOException {
        System.out.println("进入/makeComment");

        //获取用户uid
        Long uid = (Long) session.getAttribute("uid");
        //未登录或者session过期了，还是给评论的
        //获取用户信息，过期会返回passenger
        User user = userService.getUser(uid);

        Long msgid = Long.parseLong(request.getParameter("msgid"));

        Message message = messageService.getMessage(msgid);
        Comment comment = new Comment(user,message,content);
        commentRepository.save(comment);
        System.out.println("出去/makeComment");
        return "ok";
    }

}
