package com.lxy.controller;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lxy.entity.Admin;
import com.lxy.service.UserServeService;
import com.lxy.utils.ResultsPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/UserServe")
public class UserServeController {
    @Autowired
    private UserServeService userServeService;

    @ResponseBody
    @PostMapping("/login")
    public ResultsPack Logging(@RequestBody JSONObject jsonObject) throws Exception{//获取axios传递来的jason值
        String username= jsonObject.getString("username");//获取发送来的用户名
        String password= jsonObject.getString("password");
        Admin admin=new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        return userServeService.logging(admin);
    }

    @ResponseBody
    @PostMapping("/register")
    public ResultsPack Register(@RequestBody JSONObject jsonObject) throws Exception{
        String username= jsonObject.getString("username");//获取发送来的用户名
        String password= jsonObject.getString("password");
        Admin admin=new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setAuth("user");
        return userServeService.register(admin);
    }

    @ResponseBody
    @PostMapping("/getUser")//请求所有用户数据
    public ResultsPack GetUserList(@RequestBody JSONObject jsonObject) throws Exception{
        if (jsonObject.isEmpty()){
            return userServeService.getUserList(1,5);//默认一页5条
        }
        return userServeService.getUserList(jsonObject.getInteger("num"),jsonObject.getInteger("count"));//获取前端分页要求
    }

    @ResponseBody
    @PostMapping("/deleteUser")//删除用户操作
    public ResultsPack DeleteUser(@RequestBody JSONObject jsonObject) throws Exception{
        return userServeService.deleteUser(jsonObject.getInteger("id"));
    }

    @ResponseBody
    @PostMapping("/updateUser")//删除用户操作
    public ResultsPack UpdateUser(@RequestBody JSONObject jsonObject) throws Exception{
        System.out.println(jsonObject);
        return userServeService.updateUser(jsonObject.getString("username"),jsonObject.getDate("date"),jsonObject.getInteger("id"));
    }


}
