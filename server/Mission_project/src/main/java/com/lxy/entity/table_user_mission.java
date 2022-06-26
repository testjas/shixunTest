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
 * @TableName table_user_mission
 */
@TableName(value ="table_user_mission")
@Data
public class table_user_mission implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer mid;

    /**
     * 
     */
    private Integer uid;

    /**
     * 
     */
    private Integer finish;

    /**
     * 
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date joinTime;

    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}