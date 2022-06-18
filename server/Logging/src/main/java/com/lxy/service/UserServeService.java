package com.lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.entity.Admin;
import com.lxy.utils.ResultsPack;

import java.util.Date;
import java.util.List;

public interface UserServeService extends IService<Admin> {
    public ResultsPack logging(Admin admin) throws Exception;
    public ResultsPack register(Admin admin) throws Exception;
    public ResultsPack getUserList(int num,int count) throws Exception;
    public ResultsPack deleteUser(int id) throws Exception;
    public ResultsPack updateUser(String name, Date date, int id) throws Exception;
}
