package com.example.controller;

import com.example.entity.Agree;
import com.example.entity.Comment;
import com.example.entity.Message;
import com.example.entity.User;
import com.example.repository.AgreeRepository;
import com.example.repository.MessageRepository;
import com.example.service.AgreeService;
import com.example.service.CommentService;
import com.example.service.MessageService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@Controller
public class AjaxMain {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    AgreeService agreeService;

    @Autowired
    CommentService commentService;

    @Autowired
    AgreeRepository agreeRepository;

    @Autowired
    MessageRepository messageRepository;


    @RequestMapping("/ajax/main/getOrderedMsg")
    public String getMessageOrder(Model model, HttpSession session, @RequestParam(value = "method", defaultValue = "commentNum") String method)
    {
        //获取当前页和页面大小
        Integer curPage = (Integer) session.getAttribute("curPage");
        Integer size = (Integer) session.getAttribute("size");

        Page<Message> messages = messageService.listPostByMethod(curPage,size,method);


        //如果刷新了不能变啊，所以将page传给session，做两手准备
        model.addAttribute("messages",messages);


        return "main::msg";
    }

    @RequestMapping("/ajax/getComments")
    public String getCommentOrder(Model model, HttpSession session, @RequestParam(value = "method", defaultValue = "commentNum") String method)
    {
        //获取当前页和页面大小
        System.out.println("进来了/ajax/getComments");
        Integer curPage = 0;
        Integer size = 5;

        Page<Comment> comments = commentService.listPostByMethod(curPage,size,method);


        //如果刷新了不能变啊，所以将page传给session，做两手准备
        model.addAttribute("comments",comments);
        System.out.println("出去/ajax/getComments");
        model.addAttribute("commentList",comments);
        return "main::commentList";
    }

    @ResponseBody
    @GetMapping("/ajax/agree")
    public Integer makeAgree(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        System.out.println("进入/ajax/agree");
        //获取点赞的用户
        Long uid = (Long) session.getAttribute("uid");
        if (uid == null)
        {
            System.out.println("未登录");
            return 0;
        }
        System.out.println(uid);
        User user = userService.getUser(uid);

        //获取要点赞的message
        Long messsage_id = Long.parseLong(request.getParameter("msgid"));
        Message message = messageService.getMessage(messsage_id);
        Integer oldAgreeNum = message.getAggreeNum();
        System.out.println("被点赞的消息是");
        System.out.println(messsage_id);
        Integer n;

        //查询该用户对该消息点过赞没有，点过几个
        Integer countAgree = messageService.getLikeNum(message,user);
        //没点过赞就点个赞呗
        if(countAgree == 0) {
            //增加点赞记录
            agreeService.addAgree(message,user);
            //消息的被点赞数++

            n = oldAgreeNum+1;
            System.out.println("更新后的点赞数为");
            System.out.println(n);
            messageRepository.updateAgreeNum(n,messsage_id);

        }
        //点过赞了就得全部取消了
        else {
            //删除掉数据
            agreeService.delAgree(message,user);

            n = oldAgreeNum - countAgree;
            if ( n < 0) { n = 0; }
            messageRepository.updateAgreeNum(n,messsage_id);
        }
        System.out.println("我能出去吗");
        return n;

    }

}
