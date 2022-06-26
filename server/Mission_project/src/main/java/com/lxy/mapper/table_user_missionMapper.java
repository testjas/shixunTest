package com.lxy.mapper;

import com.lxy.entity.UserData;
import com.lxy.entity.table_user_mission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* @author TestJas
* @description 针对表【table_user_mission】的数据库操作Mapper
* @createDate 2022-06-13 08:52:13
* @Entity generator.entity.table_user_mission
*/
@Mapper
public interface table_user_missionMapper extends BaseMapper<table_user_mission> {
    public void deleteUM()throws Exception;
    public List<UserData> selectData(int uid) throws Exception;
}




