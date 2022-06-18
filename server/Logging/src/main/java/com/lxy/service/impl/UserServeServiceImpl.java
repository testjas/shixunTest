package com.lxy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.entity.Admin;
import com.lxy.mapper.UserServeMapper;
import com.lxy.service.UserServeService;
import com.lxy.utils.PasswordEncryption;
import com.lxy.utils.ResultsPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserServeServiceImpl extends ServiceImpl<UserServeMapper, Admin> implements UserServeService {

    @Autowired
    private UserServeMapper userServeMapper;

    @Override
    public ResultsPack logging(Admin admin) throws Exception {//登陆
        QueryWrapper<Admin> qw=new QueryWrapper<>();
        qw.eq("username",admin.getUsername());
        Admin check= userServeMapper.selectOne(qw);//获取根据用户名查询得到的值
        if (check!=null && admin.getPassword().equals(check.getPassword())){
            JSONObject jsonObject=new JSONObject();//登陆成功并把密码清除后给前端返回一个包含相关信息的json串
            jsonObject.put("username",check.getUsername());
            jsonObject.put("auth",check.getAuth());
            return new ResultsPack(true,jsonObject,"登陆成功");
        }
        return new ResultsPack(false,"登陆失败/用户名或者密码错误");
    }

    @Override
    public ResultsPack register(Admin admin) throws Exception {//注册
        QueryWrapper<Admin> qw=new QueryWrapper<>();
        qw.eq("username",admin.getUsername());
        if (userServeMapper.selectOne(qw)!=null){//判断是否存在这个用户
            return new ResultsPack(false,"该用户已被注册");
        }else{
            PasswordEncryption ps=new PasswordEncryption();//密码进入数据库前进行加密处理
            ps.setPs(admin.getPassword(), admin.getId());
            admin.setPassword(ps.getPs());
            userServeMapper.insert(admin);//插入用户
            return new ResultsPack(true,"注册成功");
        }
    }

    @Override
    public ResultsPack getUserList(int num,int count) throws Exception {//获取用户列表,num页数,count一页条数
        IPage<Admin> page=new Page<>(num,count);//分页,true表示携带返回总条数数据
        QueryWrapper qw=new QueryWrapper();//把已删除和管理员权限的人过滤掉
        qw.ne("auth","admin");
        qw.ne("isdelete",1);
        qw.select("username","auth","id","date");
        IPage<Admin> list= userServeMapper.selectPage(page,qw);//获取用户
        if (list==null){
            return new ResultsPack(false,"显示数据出错");
        }
        JSONObject jsonObject=new JSONObject();//把数据封装在json串中
        jsonObject.put("arr",list.getRecords());
        jsonObject.put("total",list.getTotal());
        jsonObject.put("num",list.getCurrent());
        jsonObject.put("count",list.getSize());
        return new ResultsPack(true,jsonObject,"查询数据成功");
    }

    @Override
    public ResultsPack deleteUser(int id) throws Exception {
        QueryWrapper<Admin> qw=new QueryWrapper<>();
        qw.eq("isdelete",0);
        qw.eq("id",id);
        Admin flag= userServeMapper.selectOne(qw);
        if (flag!=null){
            UpdateWrapper uw=new UpdateWrapper();//更新操作
            uw.set("isdelete",1);
            uw.eq("id",id);
            Admin admin= userServeMapper.selectById(id);
            userServeMapper.update(admin,uw);
            return new ResultsPack(true,"删除成功");
        }
        return new ResultsPack(false,"删除失败!该用户已被删除或出现未知错误");
    }

    @Override
    public ResultsPack updateUser(String name, Date date,int id) throws Exception {
        QueryWrapper<Admin> qw=new QueryWrapper<>();
        qw.eq("username",name);
        Admin admin= userServeMapper.selectOne(qw);
        if (admin!=null){
            return new ResultsPack(false,"更新失败或该用户名已存在");
        }
        UpdateWrapper<Admin> uw=new UpdateWrapper<>();
        uw.set("username",name);
        uw.set("date",date);
        uw.eq("id",id);
        admin= userServeMapper.selectById(id);
        userServeMapper.update(admin,uw);
        return new ResultsPack(true,"更新成功");
    }
}
