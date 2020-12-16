package com.example.service;

import com.example.entity.Comment;
import com.example.entity.Message;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    //实现分页取评论
    //当前页curPage，每页消息数量limit，消息排序方法method
    public Page<Comment> listPostByMethod(int curPage, int limit, String method)
    {
        System.out.println("进来CommentService了");

        int offset = (curPage - 1)*limit;

        System.out.println(commentRepository.count());
        int allCount =(int) commentRepository.count();
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

        Page<Comment> comments = commentRepository.findAll(pageable);
        //分页得到数据列表
        System.out.println("出去了");
        return comments;
    }
}
