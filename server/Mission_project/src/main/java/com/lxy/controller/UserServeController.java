package com.lxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxy.entity.*;
import com.lxy.mapper.table_mission_infoMapper;
import com.lxy.mapper.table_userMapper;
import com.lxy.mapper.table_user_infoMapper;
import com.lxy.mapper.table_user_missionMapper;
import com.lxy.service.*;
import com.lxy.utils.ResultsPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/UserService")
public class UserServeController {
    @Autowired
    private table_userService table_userService;
    @Autowired
    private table_user_infoService table_user_infoService;
    @Autowired
    private table_dispatcher_infoService table_dispatcher_infoService;
    @Autowired
    private table_user_missionService table_user_missionService;
    @Autowired
    private table_mission_infoService table_mission_infoService;
    @Autowired
    private table_user_missionMapper table_user_missionMapper;
    @Autowired
    private table_mission_infoMapper table_mission_infoMapper;
    @Autowired
    private table_user_infoMapper table_user_infoMapper;

    @ResponseBody
    @PostMapping("/getUserMissionList")//获取任务列表
    public ResultsPack GetUserMissionList(@RequestBody JSONObject jsonObject) throws Exception {
        //查询列表前先对任务进行更新
        table_mission_infoService.updateMissionInfo();
        //更新后在查询列表
        if (jsonObject.getJSONArray("values") == null || jsonObject.getJSONArray("values").isEmpty()) {//判断查询条件是否存在
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_mission_infoService.getUserMissionList(1, 5, jsonObject.getString("username"), null);//默认一页5条
            }
            return table_mission_infoService.getUserMissionList(jsonObject.getInteger("num"), jsonObject.getInteger("count"), jsonObject.getString("username"), null);//获取前端分页要求
        } else {//存在查询条件则进行条件查询
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_mission_infoService.getUserMissionList(1, 5, jsonObject.getString("username"), jsonObject.getJSONArray("values").getJSONObject(0));//默认一页5条
            }
            return table_mission_infoService.getUserMissionList(jsonObject.getInteger("num"), jsonObject.getInteger("count"), jsonObject.getString("username"), jsonObject.getJSONArray("values").getJSONObject(0));//获取前端分页要求
        }
    }

    @ResponseBody
    @PostMapping("/intoMission")
    public ResultsPack IntoMission(@RequestBody JSONObject jsonObject) throws Exception{
        Integer mid=jsonObject.getInteger("mid");
        String username=jsonObject.getString("username");
        if (mid==null || username==null){
            return new ResultsPack(false,"发生错误咯，请重试");
        }
        return table_user_missionService.intoMission(mid,username);
    }

    @ResponseBody
    @PostMapping("/getUserMissionInfo")
    public ResultsPack GetUserMissionInfo(@RequestBody JSONObject jsonObject)throws Exception{
        table_mission_infoService.updateMissionInfo();
        //更新后在查询列表
        if (jsonObject.getJSONArray("values") == null || jsonObject.getJSONArray("values").isEmpty()) {//判断查询条件是否存在
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_mission_infoService.getUserMissionInfo(1, 5, jsonObject.getString("username"), null);//默认一页5条
            }
            return table_mission_infoService.getUserMissionInfo(jsonObject.getInteger("num"), jsonObject.getInteger("count"), jsonObject.getString("username"), null);//获取前端分页要求
        } else {//存在查询条件则进行条件查询
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_mission_infoService.getUserMissionInfo(1, 5, jsonObject.getString("username"), jsonObject.getJSONArray("values").getJSONObject(0));//默认一页5条
            }
            return table_mission_infoService.getUserMissionInfo(jsonObject.getInteger("num"), jsonObject.getInteger("count"), jsonObject.getString("username"), jsonObject.getJSONArray("values").getJSONObject(0));//获取前端分页要求
        }
    }

    @ResponseBody
    @PostMapping("/finishSign")
    public ResultsPack FinishSign(@RequestBody JSONObject jsonObject) throws Exception{
        Integer mid= jsonObject.getInteger("mid");
        Integer uid= jsonObject.getInteger("uid");
        if (mid==null || uid==null){
            return new ResultsPack(false,"出错咯请重新试试呗？");
        }
        return table_user_missionService.signMission(mid,uid);
    }

    @ResponseBody
    @PostMapping("/finishTask")
    public ResultsPack FinishTask(@RequestBody JSONObject jsonObject) throws Exception{
        Integer mid= jsonObject.getInteger("mid");
        Integer uid= jsonObject.getInteger("uid");
        String task =jsonObject.getString("task");
        System.out.println(task);
        if (task==null ){
            return new ResultsPack(false,"你交空的是吧？是不是想挂？");
        }
        if (mid==null || uid==null){
            return new ResultsPack(false,"出错咯请重新试试呗？");
        }
        return table_user_missionService.taskMission(mid,uid,task);
    }

    @ResponseBody
    @PostMapping("/getUserData")
    public ResultsPack GetUserAuth(@RequestBody JSONObject jsonObjects)throws Exception{
        String username=jsonObjects.getString("username");
        JSONObject jsonObject=new JSONObject();
        QueryWrapper<table_user_info> tui=new QueryWrapper<>();//获取用户id
        tui.eq("username",username).select("uid");
        table_user_info table_user_info=table_user_infoMapper.selectOne(tui);
        QueryWrapper<table_user_mission> tum=new QueryWrapper<>();
        tum.eq("uid",table_user_info.getUid()).select("finish,count(*) num").groupBy("finish");
        List<Map<String, Object>> map=table_user_missionMapper.selectMaps(tum);
        for (Map<String, Object> i:map){
            Integer type= (Integer) i.get("finish");
            if (type==0){
                jsonObject.put("yes",i.get("num"));
            }else if(type==1){
                jsonObject.put("no",i.get("num"));
            }
        }

        QueryWrapper<Object> qtmi=new QueryWrapper<>();
        qtmi.select("select mission_type,count(*) from table_mission_info tmi,table_user_mission tum where tmi.mid=tum.mid and tum.uid=14 GROUP BY mission_type");
        List<UserData> ms=table_user_missionMapper.selectData(table_user_info.getUid());//分组查询任务类型
        for (UserData i:ms){
            String type= i.getType();
            if (type.equals("签到")){
                jsonObject.put("sign",i.getNum());
            }else if(type.equals("作业")){
                jsonObject.put("task",i.getNum());
            }
        }
        return new ResultsPack(true,jsonObject,"成功查询数据");
    }

}
