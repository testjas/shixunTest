package com.lxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName user_finish_info
 */
@TableName(value ="user_finish_info")
@Data
public class user_finish_info implements Serializable {
    /**
     * 
     */
    private Integer mid;

    /**
     * 
     */
    private Integer uid;

    /**
     * 
     */
    private String info;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}