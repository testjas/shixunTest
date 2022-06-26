package com.lxy.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxy.entity.table_mission_info;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.utils.ResultsPack;

/**
* @author TestJas
* @description 针对表【table_mission_info】的数据库操作Service
* @createDate 2022-06-13 08:52:13
*/
public interface table_mission_infoService extends IService<table_mission_info> {
    //管理员
    public ResultsPack getMissionList(int num,int count,JSONObject search) throws Exception;
    public void updateMissionInfo() throws Exception;
    public ResultsPack addMission(table_mission_info table_mission_info) throws Exception;
    public ResultsPack deleteMission(int mid) throws Exception;
    public ResultsPack updateMission(JSONObject jsonObject) throws Exception;
    //派发员
    public ResultsPack getDisMissionList(int num,int count,String disname,JSONObject search) throws Exception;
    public ResultsPack deleteDisMission(int mid) throws Exception;
    public ResultsPack updateDisMission(JSONObject jsonObject) throws Exception;
    public ResultsPack addDisMission(table_mission_info table_mission_info) throws Exception;
    public ResultsPack userMissionInfo(int num, int count,String disname ,JSONObject search) throws Exception;
    public ResultsPack addUserMission(JSONObject jsonObject) throws Exception;
    public ResultsPack updateUserFinish(JSONObject jsonObject) throws Exception;
    //用户
    public ResultsPack getUserMissionList(int num,int count,String username,JSONObject search) throws Exception;
    public ResultsPack getUserMissionInfo(int num, int count, String username, JSONObject search) throws Exception;
}
