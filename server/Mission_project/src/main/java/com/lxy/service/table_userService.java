package com.lxy.service;

import com.alibaba.fastjson.JSONObject;
import com.lxy.entity.table_user;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.utils.ResultsPack;

/**
* @author TestJas
* @description 针对表【table_user】的数据库操作Service
* @createDate 2022-06-13 08:52:13
*/
public interface table_userService extends IService<table_user> {
    public ResultsPack logging(table_user user) throws Exception;
    public ResultsPack register(table_user user) throws Exception;
    public ResultsPack getUserList(int num, int count, JSONObject search) throws Exception;
    public ResultsPack deleteUser(int id,int isdelete) throws Exception;
    public ResultsPack getUser(int id,String auth) throws Exception;
}
