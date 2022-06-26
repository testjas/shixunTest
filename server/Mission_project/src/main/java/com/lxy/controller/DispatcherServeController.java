package com.lxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lxy.entity.table_mission_info;
import com.lxy.entity.table_user;
import com.lxy.entity.table_user_info;
import com.lxy.entity.table_user_mission;
import com.lxy.mapper.table_mission_infoMapper;
import com.lxy.mapper.table_userMapper;
import com.lxy.mapper.table_user_missionMapper;
import com.lxy.service.table_dispatcher_infoService;
import com.lxy.service.table_mission_infoService;
import com.lxy.service.table_userService;
import com.lxy.service.table_user_infoService;
import com.lxy.utils.ResultsPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/DisServe")
public class DispatcherServeController {//任务派发员
    @Autowired
    private table_userService table_userService;
    @Autowired
    private table_user_infoService table_user_infoService;
    @Autowired
    private table_dispatcher_infoService table_dispatcher_infoService;
    @Autowired
    private table_mission_infoService table_mission_infoService;
    @Autowired
    private table_user_missionMapper table_user_missionMapper;

    @Autowired
    private table_mission_infoMapper table_mission_infoMapper;

    @Autowired
    private com.lxy.mapper.table_userMapper table_userMapper;

    @ResponseBody
    @PostMapping("/getDisMissionList")//获取任务列表
    public ResultsPack GetMission(@RequestBody JSONObject jsonObject) throws Exception {
        //查询列表前先对任务进行更新
        table_mission_infoService.updateMissionInfo();
        //更新后在查询列表
        if (jsonObject.getJSONArray("values") == null || jsonObject.getJSONArray("values").isEmpty()) {//判断查询条件是否存在
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_mission_infoService.getDisMissionList(1, 5, jsonObject.getString("username"), null);//默认一页5条
            }
            return table_mission_infoService.getDisMissionList(jsonObject.getInteger("num"), jsonObject.getInteger("count"), jsonObject.getString("username"), null);//获取前端分页要求
        } else {//存在查询条件则进行条件查询
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_mission_infoService.getDisMissionList(1, 5, jsonObject.getString("username"), jsonObject.getJSONArray("values").getJSONObject(0));//默认一页5条
            }
            return table_mission_infoService.getDisMissionList(jsonObject.getInteger("num"), jsonObject.getInteger("count"), jsonObject.getString("username"), jsonObject.getJSONArray("values").getJSONObject(0));//获取前端分页要求
        }
    }

    @ResponseBody
    @PostMapping("/deleteDisMission")//删除任务
    public ResultsPack DeleteDisMission(@RequestBody JSONObject jsonObject) throws Exception {
        int mid = jsonObject.getInteger("mid");
        return table_mission_infoService.deleteDisMission(mid);
    }

    @ResponseBody
    @PostMapping("/updateDisMission")//更新任务
    public ResultsPack updateDisMission(@RequestBody JSONObject jsonObject) throws Exception {
        return table_mission_infoService.updateDisMission(jsonObject);
    }

    @ResponseBody
    @PostMapping("/addDisMission")
    public ResultsPack AddDisMission(@RequestBody table_mission_info table_mission_info) throws Exception {
        return table_mission_infoService.addDisMission(table_mission_info);
    }

    @ResponseBody
    @PostMapping("/getUserMissionInfo")
    public ResultsPack GetUserMissionInfo(@RequestBody JSONObject jsonObject) throws Exception {
//        System.out.println(jsonObject);
        //查询列表前先对任务进行更新
        table_mission_infoService.updateMissionInfo();
        //更新后在查询列表
        if (jsonObject.getJSONArray("values") == null || jsonObject.getJSONArray("values").isEmpty()) {//判断查询条件是否存在
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_mission_infoService.userMissionInfo(1, 5, jsonObject.getString("username"), null);//默认一页5条
            }
            return table_mission_infoService.userMissionInfo(jsonObject.getInteger("num"), jsonObject.getInteger("count"), jsonObject.getString("username"), null);//获取前端分页要求
        } else {//存在查询条件则进行条件查询
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_mission_infoService.userMissionInfo(1, 5, jsonObject.getString("username"), jsonObject.getJSONArray("values").getJSONObject(0));//默认一页5条
            }
            return table_mission_infoService.userMissionInfo(jsonObject.getInteger("num"), jsonObject.getInteger("count"), jsonObject.getString("username"), jsonObject.getJSONArray("values").getJSONObject(0));//获取前端分页要求
        }
    }

    @ResponseBody
    @PostMapping("/addUserMission")
    public ResultsPack addUserMission(@RequestBody JSONObject jsonObject) throws Exception {
        return table_mission_infoService.addUserMission(jsonObject);
    }

    @ResponseBody
    @PostMapping("/deleteUserMission")
    public ResultsPack deleteUserMission(@RequestBody JSONObject jsonObject) throws Exception {
        System.out.println(jsonObject);
        Integer uid = jsonObject.getInteger("uid");
        Integer mid = jsonObject.getInteger("mid");
        String disName=jsonObject.getString("disName");
        QueryWrapper<table_user> tui=new QueryWrapper<>();//判断是否有权限且是否为派发员
        tui.eq("username",disName).eq("auth","dispatcher");
        table_user table_user=table_userService.getOne(tui);
        if (table_user!=null){//进行删除操作
            UpdateWrapper<table_user_mission> tum=new UpdateWrapper<>();
            tum.eq("mid",mid).eq("uid",uid).set("isdelete",1);
            QueryWrapper<table_user_mission> qw=new QueryWrapper<>();
            qw.eq("mid",mid);
            qw.eq("uid",uid);
            table_user_mission table_user_mission=table_user_missionMapper.selectOne(qw);
            int a=table_user_missionMapper.update(table_user_mission,tum);
            if (a>0){
                return new ResultsPack(true,"删除成功");
            }else{
                return new ResultsPack(false,"操作失败啦，请重新试试~");
            }
        }else{
            return new ResultsPack(false,"你不可以这样做哦~");
        }
    }

    @ResponseBody
    @PostMapping("/updateUserFinish")
    public ResultsPack UpdateUserFinish(@RequestBody JSONObject jsonObject) throws Exception{
        return table_mission_infoService.updateUserFinish(jsonObject);
    }

    @ResponseBody
    @PostMapping("/getDisData")
    public ResultsPack GetUserAuth(@RequestBody JSONObject jsonObjects)throws Exception{
        String username=jsonObjects.getString("username");
        JSONObject jsonObject=new JSONObject();
        QueryWrapper<table_mission_info> tmi=new QueryWrapper<>();
        tmi.eq("belong",username).select("sum(joined_num) num");
        List<Map<String, Object>> map=table_mission_infoMapper.selectMaps(tmi);
        jsonObject.put("user",map.get(0).get("num"));

        QueryWrapper<table_mission_info> qtmi=new QueryWrapper<>();
        qtmi.eq("belong",username).groupBy("mission_type").select("mission_type,count(*) num");
        List<Map<String, Object>> ms=table_mission_infoMapper.selectMaps(qtmi);//分组查询任务类型
        for (Map<String, Object> i:ms){
            String type= (String) i.get("mission_type");
            if (type.equals("签到")){
                jsonObject.put("sign",i.get("num"));
            }else if(type.equals("作业")){
                jsonObject.put("task",i.get("num"));
            }
        }
        return new ResultsPack(true,jsonObject,"成功查询数据");
    }
}
