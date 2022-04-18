package com.lxy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.dao.UserServeDao;
import com.lxy.entity.Admin;
import com.lxy.service.UserServeService;
import com.lxy.utils.ResultsPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServeServiceImpl extends ServiceImpl<UserServeDao, Admin> implements UserServeService {

    @Autowired
    private UserServeDao userServeDao;

    @Override
    public ResultsPack logging(Admin admin) {//登陆
        QueryWrapper<Admin> qw=new QueryWrapper<>();
        qw.eq("username",admin.getUsername());
        Admin check= userServeDao.selectOne(qw);//获取根据用户名查询得到的值
        if (check.getPassword().equals(admin.getPassword())){
            JSONObject jsonObject=new JSONObject();//登陆成功并把密码清除后给前端返回一个包含相关信息的json串
            jsonObject.put("username",check.getUsername());
            jsonObject.put("auth",check.getAuth());
            return new ResultsPack(true,jsonObject,"登陆成功");
        }
        return new ResultsPack(false,"登陆失败/用户名或者密码错误");
    }

    @Override
    public ResultsPack register(Admin admin) {//注册
        QueryWrapper<Admin> qw=new QueryWrapper<>();
        qw.eq("username",admin.getUsername());
        if (userServeDao.selectOne(qw)!=null){//判断是否存在这个用户
            return new ResultsPack(false,"该用户已被注册");
        }else{
            userServeDao.insert(admin);//插入用户
            return new ResultsPack(true,"注册成功");
        }
    }
}
