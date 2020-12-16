package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//信息自身的Id

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private User userFrom;
    private String fromNickName;
    private Long fromId;

    //发出评论用户是否登陆
    private Boolean login;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Message message;
    private Long toMessageId;

    private Date createDate;
    private Integer agreeNum;
    private String content;

    public Comment(User userFrom,Message message,String content)
    {
        this.userFrom = userFrom;
        this.fromId = userFrom.getId();
        this.fromNickName = userFrom.getNickName();
        this.message = message;
        this.toMessageId = message.getId();
        this.createDate = new Date();
        this.agreeNum = 0;
        this.content = content;
    }
    public Comment(){

    }
}
