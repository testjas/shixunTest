package com.lxy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class user_mission_list_info {
    private Integer mid;

    private Integer uid;

    private Integer finish;

    private String belong;

    private String missionName;

    private String description;

    private Integer userNum;

    private Integer joinedNum;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String status;

    private Integer finishStatus;

    private String timeStatus;

    private String missionType;

    private Integer isdelete;

    private String info;
}
