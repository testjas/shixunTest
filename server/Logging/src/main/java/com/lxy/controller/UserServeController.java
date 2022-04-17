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
    public ResultsPack Logging(@RequestBody JSONObject jsonObject){//获取axios传递来的jason值
        String username= jsonObject.getString("username");//获取发送来的用户名
        String password= jsonObject.getString("password");
        Admin admin=new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        return userServeService.logging(admin);
    }

    @ResponseBody
    @PostMapping("/register")
    public ResultsPack Register(@RequestBody JSONObject jsonObject){
        String username= jsonObject.getString("username");//获取发送来的用户名
        String password= jsonObject.getString("password");
        Admin admin=new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        return userServeService.register(admin);
    }
}
