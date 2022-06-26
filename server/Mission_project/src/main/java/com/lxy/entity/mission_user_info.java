package com.lxy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class mission_user_info {//派发员用户信息列表

    private Integer uid;

    private Integer mid;

    private String username;

    private String missionName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date joinTime;

    private Integer finish;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

}
