package com.lxy.service;

import com.lxy.entity.table_user_mission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.utils.ResultsPack;

/**
* @author TestJas
* @description 针对表【table_user_mission】的数据库操作Service
* @createDate 2022-06-13 08:52:13
*/
public interface table_user_missionService extends IService<table_user_mission> {
    public ResultsPack intoMission(int mid,String username) throws Exception;
    public ResultsPack signMission(int mid,int uid) throws Exception;
    public ResultsPack taskMission(int mid,int uid,String task) throws Exception;
}
