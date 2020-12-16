package com.example.service;

import com.example.entity.Agree;
import com.example.entity.Message;
import com.example.entity.User;
import com.example.repository.AgreeRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AgreeRepository agreeRepository;

    //实现分页取评论
    //当前页curPage，每页消息数量limit，消息排序方法method
    public Page<Message> listPostByMethod(int curPage, int limit, String method)
    {


        int offset = (curPage - 1)*limit;

        System.out.println(messageRepository.count());
        int allCount =(int) messageRepository.count();
        int allPage = 0;
        if (allPage <= limit)
        {
            allPage = 1;
        }else if (allCount / limit == 0) {
            allPage = allCount / limit;
        } else {
            allPage = allCount / limit + 1;
        }

        Sort sort = Sort.by(Sort.Direction.DESC,method);
        Pageable pageable = PageRequest.of(curPage,limit,sort);
        Page<Message> messages = messageRepository.findAll(pageable);
        //分页得到数据列表

        return messages;
    }

    //某用户是否赞过某帖子
    //参数，消息id和用户
    //返回值，用户点过赞，返回 - 点过的赞数（测试和错误可能会用多个）
    //没点过赞，返回0
    public Integer getLikeNum(Message message, User user) {
        System.out.println("进来了");
        List<Agree> agrees = agreeRepository.findAgreesByUserAndMessage(user,message);
        System.out.println("我走了");
        return agrees.size();
    }

    //根据消息id获取消息
    public Message getMessage(Long id){
        Message message = new Message();
        List<Message> messages = messageRepository.findAllById(id);
        if (messages.size() == 0)
            return message;
        else if(messages.size() > 1)
        {
            System.out.println("消息Id不可能重叠哒");
        }
        message = messages.get(0);
        return  message;
    }


}
