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
 * @TableName table_mission_info
 */
@TableName(value ="table_mission_info")
@Data
public class table_mission_info implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer mid;

    /**
     * 
     */
    private String missionName;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Integer userNum;

    /**
     * 
     */
    private Integer joinedNum;

    /**
     * 
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 
     */
    private String status;

    /**
     *
     */
    private Integer finishStatus;

    /**
     * 
     */
    private String timeStatus;

    /**
     *
     */
    private String belong;

    /**
     * 
     */
    private String missionType;

    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}