package com.lxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.entity.table_user;
import com.lxy.entity.table_user_info;
import com.lxy.entity.table_user_mission;
import com.lxy.entity.user_finish_info;
import com.lxy.mapper.table_userMapper;
import com.lxy.mapper.table_user_missionMapper;
import com.lxy.mapper.user_finish_infoMapper;
import com.lxy.service.table_user_missionService;
import com.lxy.utils.ResultsPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author TestJas
* @description 针对表【table_user_mission】的数据库操作Service实现
* @createDate 2022-06-13 08:52:13
*/
@Service
public class table_user_missionServiceImpl extends ServiceImpl<table_user_missionMapper, table_user_mission>
    implements table_user_missionService{
    @Autowired
    private table_userMapper table_userMapper;
    @Autowired
    private table_user_missionMapper table_user_missionMapper;
    @Autowired
    private user_finish_infoMapper user_finish_infoMapper;

    @Override
    public ResultsPack intoMission(int mid, String username) throws Exception {
        QueryWrapper<table_user> qw=new QueryWrapper<>();
        qw.eq("username",username).ne("isdelete",1).eq("auth","user").select("id");
        table_user tu=table_userMapper.selectOne(qw);
        QueryWrapper<table_user_mission> tum=new QueryWrapper<>();
        tum.eq("mid",mid).eq("uid",tu.getId());
        table_user_mission table_user_mission=table_user_missionMapper.selectOne(tum);
        if (table_user_mission!=null){
            return new ResultsPack(false,"该用户已经加入过该任务");
        }else{
            table_user_mission tableUserMission=new table_user_mission();
            tableUserMission.setMid(mid);
            tableUserMission.setUid(tu.getId());
            tableUserMission.setJoinTime(new Date());
            int a=table_user_missionMapper.insert(tableUserMission);
            if (a>0){
                return new ResultsPack(true,"成功加入该任务");
            }else {
                return new ResultsPack(false,"加入失败请重试");
            }
        }
    }

    @Override
    public ResultsPack signMission(int mid, int uid) throws Exception {
        QueryWrapper<table_user_mission> qw=new QueryWrapper<>();
        qw.eq("mid",mid).eq("uid",uid);
        if (table_user_missionMapper.selectOne(qw.eq("finish",1))!=null){//如果已经完成过该任务则
            return new ResultsPack(false,"已经完成过了，不用重复了哦~");
        }
        table_user_mission table_user_mission=table_user_missionMapper.selectOne(qw);
        UpdateWrapper<table_user_mission> uw=new UpdateWrapper<>();
        uw.set("finish",1).set("finish_time",new Date()).eq("mid",mid).eq("uid",uid);
        int a=table_user_missionMapper.update(table_user_mission,uw);
        if (a>0){
            return new ResultsPack(true,"成功完成签到哦~");
        }else {
            return new ResultsPack(false,"失败啦，请重新试试看啦~");
        }
    }

    @Override
    public ResultsPack taskMission(int mid, int uid,String task) throws Exception {
        QueryWrapper<table_user_mission> qw=new QueryWrapper<>();
        qw.eq("mid",mid).eq("uid",uid);
        if (table_user_missionMapper.selectOne(qw.eq("finish",1))!=null){//如果已经完成过该任务则
            return new ResultsPack(false,"已经完成过了，不用重复了哦~");
        }
        table_user_mission table_user_mission=table_user_missionMapper.selectOne(qw);
        UpdateWrapper<table_user_mission> uw=new UpdateWrapper<>();
        uw.set("finish",1).set("finish_time",new Date()).eq("mid",mid).eq("uid",uid);
        QueryWrapper<user_finish_info> ufi=new QueryWrapper<>();
        ufi.eq("mid",mid).eq("uid",uid);
        user_finish_info user_finish_info=user_finish_infoMapper.selectOne(ufi);
        UpdateWrapper<user_finish_info> uufi=new UpdateWrapper<>();
        uufi.eq("mid",mid).eq("uid",uid).set("info",task);
        int a=user_finish_infoMapper.update(user_finish_info,uufi);
        int b=table_user_missionMapper.update(table_user_mission,uw);
        if (a>0 && b>0){
            return new ResultsPack(true,"成功提交任务了哦~");
        }else {
            return new ResultsPack(false,"失败啦，请重新试试看啦~");
        }
    }
}




