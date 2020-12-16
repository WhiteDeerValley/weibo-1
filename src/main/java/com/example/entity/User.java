package com.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //系统属性
    private Integer actived;
    private String activateCode;
    private String joinTime;


    private String email;
    private String nickName;
    private String userName;
    private String passWord;
    private String passWordAgain;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Agree> agrees = new ArrayList<>();

    public User(String email,String nickName,String userName,String passWord, String passWordAgain){
        this.email = email;
        this.nickName = nickName;
        this.userName = userName;
        this.passWord = passWord;
        this.passWordAgain = passWordAgain;

    }
    public User(){

    }
}
