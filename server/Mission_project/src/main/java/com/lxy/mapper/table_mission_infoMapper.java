package com.lxy.mapper;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lxy.entity.mission_user_info;
import com.lxy.entity.user_mission_list_info;
import org.apache.ibatis.annotations.Param;

import com.lxy.entity.table_mission_info;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.Map;


/**
 * @author TestJas
 * @description 针对表【table_mission_info】的数据库操作Mapper
 * @createDate 2022-06-13 08:52:13
 * @Entity generator.entity.table_mission_info
 */
@Mapper
public interface table_mission_infoMapper extends BaseMapper<table_mission_info> {
    public void updateTime() throws Exception;

    public void updateStatus() throws Exception;

    public void deleteMission() throws Exception;

    public ArrayList<mission_user_info> userMissionInfo(String userName, String missionName,
                                                        Integer finish, String startJoin,
                                                        String endJoin, String startFinish,
                                                        String endFinish,
                                                        String disName,
                                                        Integer num, Integer count) throws Exception;

    public IPage<user_mission_list_info> userMissionInfoList(@Param("map") Map<String,Object> map, IPage<user_mission_list_info> ipage) throws Exception;


}




