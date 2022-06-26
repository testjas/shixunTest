package com.lxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName table_dispatcher_info
 */
@TableName(value ="table_dispatcher_info")
@Data
public class table_dispatcher_info implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer uid;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 
     */
    private Integer missionNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}