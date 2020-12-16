package com.example.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;//信息自身的Id

    //发布者id
    private Long fromId;
    //发布者昵称
    private String fromNickName;
    //消息内容
    private String content;
    //创建时间
    private Date createDate;

    //已被读
    private int hasRead;

    //被点赞人数
    private int aggreeNum;
    //被评论数
    private int commentNum;
    private String messageUrl;



    public Message(Long fromId, String fromNickName, String content, String messageUrl)
    {
        this.fromId = fromId;
        this.fromNickName = fromNickName;
        this.content = content;
        this.createDate = new Date();
        this.hasRead = 0;
        this.aggreeNum = 0;
        this.commentNum = 0;
        this.messageUrl = messageUrl;
    }
    public Message()
    {

    }
}
