package com.lxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("admin")
public class Admin implements Serializable {
    private static final long serialVersionUID = -20590655740338184L;
    
    private String username;
    
    private String password;
    @TableId(type = IdType.AUTO)//id自增
    private Integer id;

    private String auth;


}

