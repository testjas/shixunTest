package com.lxy.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxy.entity.table_dispatcher_info;
import com.lxy.entity.table_mission_info;
import com.lxy.entity.table_user;
import com.lxy.entity.table_user_info;
import com.lxy.mapper.table_mission_infoMapper;
import com.lxy.mapper.table_userMapper;
import com.lxy.service.table_dispatcher_infoService;
import com.lxy.service.table_mission_infoService;
import com.lxy.service.table_userService;
import com.lxy.service.table_user_infoService;
import com.lxy.utils.JwtUtil;
import com.lxy.utils.ResultsPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/UserServe")
//@CrossOrigin("**")
public class AdminServeController {//admin管理员+登录注册管理
    @Autowired
    private table_userService table_userService;
    @Autowired
    private table_user_infoService table_user_infoService;
    @Autowired
    private table_dispatcher_infoService table_dispatcher_infoService;
    @Autowired
    private table_mission_infoService table_mission_infoService;
    @Autowired
    private com.lxy.mapper.table_mission_infoMapper table_mission_infoMapper;
    @Autowired
    private com.lxy.mapper.table_userMapper table_userMapper;

    @ResponseBody
    @PostMapping("/token")
    public ResultsPack Token(@RequestBody JSONObject jsonObject) throws Exception{
        System.out.println(jsonObject);
        Boolean f=JwtUtil.checkToken(jsonObject.getString("token"));
        if (f){
            return new ResultsPack(true,"校验成功");
        }else{
            return new ResultsPack(false,"token失效或者错误啦，请重新登陆试试");
        }
    }

    @ResponseBody
    @PostMapping("/login")
    public ResultsPack Logging(@RequestBody JSONObject jsonObject) throws Exception {//获取axios传递来的jason值
        String username = jsonObject.getString("username");//获取发送来的用户名
        String password = jsonObject.getString("password");
        table_user user = new table_user();
        user.setUsername(username);
        user.setPassword(password);
        return table_userService.logging(user);
    }

    @ResponseBody
    @PostMapping("/register")
    public ResultsPack Register(@RequestBody JSONObject jsonObject) throws Exception {
        String username = jsonObject.getString("username");//获取发送来的用户名
        String password = jsonObject.getString("password");//获取发送来的密码
        String auth = jsonObject.getString("auth");
        table_user user = new table_user();
        user.setUsername(username);
        user.setPassword(password);
        if (auth != null) {
            user.setAuth(auth);
        } else {
            user.setAuth("user");
        }
        user.setCreateTime(new Date());
        user.setIsdelete(0);
        return table_userService.register(user);
    }

    @ResponseBody
    @PostMapping("/getUserList")//请求所有用户数据
    public ResultsPack GetUserList(@RequestBody JSONObject jsonObject) throws Exception {
        if (jsonObject.getJSONArray("values") == null || jsonObject.getJSONArray("values").isEmpty()) {//判断查询条件是否存在
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_userService.getUserList(1, 5, null);//默认一页5条
            }
            return table_userService.getUserList(jsonObject.getInteger("num"), jsonObject.getInteger("count"), null);//获取前端分页要求
        } else {//存在查询条件则进行条件查询
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_userService.getUserList(1, 5, jsonObject.getJSONArray("values").getJSONObject(0));//默认一页5条
            }
            return table_userService.getUserList(jsonObject.getInteger("num"), jsonObject.getInteger("count"), jsonObject.getJSONArray("values").getJSONObject(0));//获取前端分页要求
        }

    }

    @ResponseBody
    @PostMapping("/deleteUser")//删除用户操作
    public ResultsPack DeleteUser(@RequestBody JSONObject jsonObject) throws Exception {
        return table_userService.deleteUser(jsonObject.getInteger("id"), 1);
    }

    @ResponseBody
    @PostMapping("/updateUser")//删除用户操作
    public ResultsPack UpdateUser(@RequestBody JSONObject jsonObject) throws Exception {
        int id = jsonObject.getInteger("id");
        String auth = jsonObject.getString("auth");
        String name = jsonObject.getString("name");
        String prename = jsonObject.getString("prename");
        String description = jsonObject.getString("description");
        String isdelete = jsonObject.getString("isdelete");
        if (auth.equals("user")) {
            if (isdelete != null) {
                if (isdelete.equals("active")) {
                    table_userService.deleteUser(id, 0);
                } else if (isdelete.equals("delete")) {
                    table_userService.deleteUser(id, 1);
                }
            }
            return table_user_infoService.updateUser(id, name, prename, description);
        } else if (auth.equals("dispatcher")) {
            if (isdelete != null) {
                if (isdelete.equals("active")) {
                    table_userService.deleteUser(id, 0);
                } else if (isdelete.equals("delete")) {
                    table_userService.deleteUser(id, 1);
                }
            }
            return table_dispatcher_infoService.updateUser(id, name, prename, description);
        }
        return new ResultsPack(false, "不好意思发生了意外的错误~");
    }

    @ResponseBody
    @PostMapping("/getUser")//获取单个用户操作
    public ResultsPack GetUser(@RequestBody JSONObject jsonObject) throws Exception {
        return table_userService.getUser(jsonObject.getInteger("id"), jsonObject.getString("auth"));
    }

    @ResponseBody
    @PostMapping("/getMissionList")
    public ResultsPack GetMission(@RequestBody JSONObject jsonObject) throws Exception {
        //查询列表前先对任务进行更新
        table_mission_infoService.updateMissionInfo();
        //更新后在查询列表
        if (jsonObject.getJSONArray("values") == null || jsonObject.getJSONArray("values").isEmpty()) {//判断查询条件是否存在
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_mission_infoService.getMissionList(1, 5, null);//默认一页5条
            }
            return table_mission_infoService.getMissionList(jsonObject.getInteger("num"), jsonObject.getInteger("count"), null);//获取前端分页要求
        } else {//存在查询条件则进行条件查询
            if (jsonObject.isEmpty() || jsonObject.getString("count") == null) {
                return table_mission_infoService.getMissionList(1, 5, jsonObject.getJSONArray("values").getJSONObject(0));//默认一页5条
            }
            return table_mission_infoService.getMissionList(jsonObject.getInteger("num"), jsonObject.getInteger("count"), jsonObject.getJSONArray("values").getJSONObject(0));//获取前端分页要求
        }
    }

    @ResponseBody
    @PostMapping("/addMission")
    public ResultsPack AddMission(@RequestBody table_mission_info table_mission_info) throws Exception {
        return table_mission_infoService.addMission(table_mission_info);
    }

    @ResponseBody
    @PostMapping("/deleteMission")
    public ResultsPack deleteMission(@RequestBody JSONObject jsonObject) throws Exception {
        int mid=jsonObject.getInteger("mid");
        return table_mission_infoService.deleteMission(mid);
    }

    @ResponseBody
    @PostMapping("/updateMission")
    public ResultsPack updateMission(@RequestBody JSONObject jsonObject) throws Exception{
        return table_mission_infoService.updateMission(jsonObject);
    }

    @ResponseBody
    @PostMapping("/getLoginTime")
    public ResultsPack getLoginTime(@RequestBody JSONObject jsonObject){
        int id=jsonObject.getInteger("uid");
        QueryWrapper<table_user> qw=new QueryWrapper<>();
        qw.eq("id",id);
        table_user table_user=table_userService.getOne(qw);
        if (table_user.getAuth().equals("user")){
            QueryWrapper<table_user_info> tui=new QueryWrapper<>();
            tui.eq("uid",id);
            tui.select("login_time");
            table_user_info table_user_info=table_user_infoService.getOne(tui);
            if (table_user_info!=null){
                return new ResultsPack(true,table_user_info.getLoginTime(),"成功");
            }else{
                return new ResultsPack(true,"该用户未登录过","成功");
            }
        }else if(table_user.getAuth().equals("dispatcher")){
            QueryWrapper<table_dispatcher_info> tdi=new QueryWrapper<>();
            tdi.eq("uid",id);
            tdi.select("login_time");
            table_dispatcher_info table_dispatcher_info=table_dispatcher_infoService.getOne(tdi);
            if (table_dispatcher_info!=null){
                return new ResultsPack(true,table_dispatcher_info.getLoginTime().toString(),"成功");
            }else{
                return new ResultsPack(true,"该用户未登录过","成功");
            }
        }
        return new ResultsPack(false,"登陆时间获取错误");
    }

    @ResponseBody
    @PostMapping("/getUserAuth")
    public ResultsPack GetUserAuth()throws Exception{
        JSONObject jsonObject=new JSONObject();
        QueryWrapper<table_user> qtu=new QueryWrapper<>();
        qtu.groupBy("auth").select("auth,count(*) num");
        List<Map<String, Object>> ts=table_userMapper.selectMaps(qtu);//分组查询用户
        for (Map<String, Object> i:ts){
            String auth= (String) i.get("auth");
            if (auth.equals("user")){
                jsonObject.put("user",i.get("num"));
            }else if(auth.equals("dispatcher")){
                jsonObject.put("dispatcher",i.get("num"));
            }
        }

        QueryWrapper<table_mission_info> qtmi=new QueryWrapper<>();
        qtmi.groupBy("mission_type").select("mission_type,count(*) num");
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
