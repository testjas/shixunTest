package com.lxy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.entity.table_dispatcher_info;
import com.lxy.entity.table_mission_info;
import com.lxy.entity.table_user;
import com.lxy.entity.table_user_info;
import com.lxy.mapper.*;
import com.lxy.service.table_userService;
import com.lxy.utils.JwtUtil;
import com.lxy.utils.PasswordEncryption;
import com.lxy.utils.ResultsPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author TestJas
 * @description 针对表【table_user】的数据库操作Service实现
 * @createDate 2022-06-13 08:52:13
 */
@Service
public class table_userServiceImpl extends ServiceImpl<table_userMapper, table_user>
        implements table_userService {

    @Autowired
    private table_userMapper table_userMapper;
    @Autowired
    private table_user_infoMapper table_user_infoMapper;
    @Autowired
    private table_dispatcher_infoMapper table_dispatcher_infoMapper;
    @Autowired
    private com.lxy.mapper.table_mission_infoMapper table_mission_infoMapper;
    @Autowired
    private com.lxy.mapper.table_user_missionMapper table_user_missionMapper;


    @Override
    public ResultsPack logging(table_user user) throws Exception {//登陆
        QueryWrapper<table_user> qw = new QueryWrapper<>();
        qw.eq("username", user.getUsername());
        table_user check = table_userMapper.selectOne(qw);
        if (check != null && user.getPassword().equals(check.getPassword()) && check.getIsdelete()!=1) {//如果查到这个用户
            if (check.getAuth().equals("user")){
               UpdateWrapper<table_user_info> up=new UpdateWrapper<>();
               up.set("login_time",new Date());
               up.eq("uid",check.getId());
               table_user_info tui=table_user_infoMapper.selectById(check.getId());
               table_user_infoMapper.update(tui,up);
            }else if(check.getAuth().equals("dispatcher")){
                UpdateWrapper<table_dispatcher_info> up=new UpdateWrapper<>();
                up.set("login_time",new Date());
                up.eq("uid",check.getId());
                table_dispatcher_info tui=table_dispatcher_infoMapper.selectById(check.getId());
                table_dispatcher_infoMapper.update(tui,up);
            }
            JSONObject jsonObject = new JSONObject();//登陆成功并把密码清除后给前端返回一个包含相关信息的json串
            String token=JwtUtil.createToken(check.getId().toString(),check.getUsername(),check.getAuth());
            jsonObject.put("token",token);
            jsonObject.put("username", check.getUsername());
            jsonObject.put("auth", check.getAuth());
            return new ResultsPack(true, jsonObject, "登陆成功");
        }
        return new ResultsPack(false, "登陆失败/用户名或者密码错误");
    }

    @Override
    public ResultsPack register(table_user user) throws Exception {
        QueryWrapper<table_user> qw = new QueryWrapper<>();
        qw.eq("username", user.getUsername());
        if (table_userMapper.selectOne(qw) != null) {//判断是否存在这个用户
            return new ResultsPack(false, "该用户已被注册");
        } else {
            table_userMapper.insert(user);//插入用户
            return new ResultsPack(true, "注册成功");
        }
    }

    @Override
    public ResultsPack getUserList(int num, int count,JSONObject search ) throws Exception {
        IPage<table_user> page = new Page<>(num, count);//分页,true表示携带返回总条数数据
        QueryWrapper qw = new QueryWrapper();//把已删除和管理员权限的人过滤掉
        qw.ne("auth", "admin");
        qw.select("username", "create_time", "auth", "id","isdelete");
        if (search!=null){
            if (!search.isEmpty()){
                String name=search.getString("uname");
                if (name!=null){
                    qw.like("username",name);//用户名条件查询
                }
                String auth=search.getString("userauth");
                if (auth!=null){
                    qw.eq("auth",auth);
                }
                String start="";
                String end="";
                if (search.getJSONArray("create")!=null){//判断是否有日期
                    start= (String) search.getJSONArray("create").get(0);
                    end=(String) search.getJSONArray("create").get(1);
                    qw.between("create_time",start,end);
                }
            }
        }
        IPage<table_user> list = table_userMapper.selectPage(page, qw);//获取用户
        if (list == null) {
            return new ResultsPack(false, "显示数据出错");
        }

        JSONObject jsonObject = new JSONObject();//把数据封装在json串中
        for (table_user tb:list.getRecords()){
            if (tb.getAuth().equals("dispatcher")){//投机取巧把logintime装入到paasword中，可以省略多表查询
                QueryWrapper<table_dispatcher_info> tdi=new QueryWrapper<>();
                tdi.eq("uid",tb.getId());
                table_dispatcher_info table_dispatcher_info=table_dispatcher_infoMapper.selectOne(tdi);
                if (table_dispatcher_info.getLoginTime()!=null){
                    tb.setPassword(table_dispatcher_info.getLoginTime().toString());
                }else{
                    tb.setPassword("该用户未登陆过");
                }

            }else if (tb.getAuth().equals("user")){
                QueryWrapper<table_user_info> tui=new QueryWrapper<>();
                tui.eq("uid",tb.getId());
                table_user_info table_user_info=table_user_infoMapper.selectOne(tui);
                if (table_user_info.getLoginTime()!=null){
                    tb.setPassword(table_user_info.getLoginTime().toString());
                }else{
                    tb.setPassword("该用户未登陆过");
                }
            }
        }
        jsonObject.put("arr", list.getRecords());
        jsonObject.put("total", list.getTotal());
        jsonObject.put("num", list.getCurrent());
        jsonObject.put("count", list.getSize());
        return new ResultsPack(true, jsonObject, "查询数据成功");
    }

    @Override
    public ResultsPack deleteUser(int id,int isdelete) throws Exception {//删除用户
        QueryWrapper<table_user> qw = new QueryWrapper<>();
        qw.eq("isdelete", 0);
        qw.eq("id", id);
        table_user flag = table_userMapper.selectOne(qw);
        if (flag != null) {
            if (isdelete==1){
                UpdateWrapper uw = new UpdateWrapper();//更新操作
                uw.set("isdelete", 1);
                uw.eq("id", id);
                table_user admin = table_userMapper.selectById(id);
                table_userMapper.update(admin, uw);
                return new ResultsPack(true, "删除成功");
            }
        }else{
            if (isdelete==0){
                UpdateWrapper uw = new UpdateWrapper();//更新操作
                uw.set("isdelete", 0);
                uw.eq("id", id);
                table_user admin = table_userMapper.selectById(id);
                table_userMapper.update(admin, uw);
            }
        }
        return new ResultsPack(false, "删除失败!该用户已被删除或出现未知错误");
    }

    @Override
    public ResultsPack getUser(int id, String auth) throws Exception {
        if (auth.equals("dispatcher")) {
            return new ResultsPack(true, table_dispatcher_infoMapper.selectById(id), "查询单个用户成功");
        }
        return new ResultsPack(true, table_user_infoMapper.selectById(id), "查询单个用户成功");
    }



}




