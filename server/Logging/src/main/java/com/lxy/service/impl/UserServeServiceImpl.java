package com.lxy.service.impl;

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
        Admin check= userServeDao.selectById(admin.getUsername());//获取根据用户名查询得到的值
        if (check.getPassword().equals(admin.getPassword())){
            return new ResultsPack(true,check);
        }
        return new ResultsPack(false);
    }

    @Override
    public ResultsPack register(Admin admin) {//注册
        if (userServeDao.selectById(admin.getUsername())==null){//判断是否存在这个用户
            userServeDao.insert(admin);//插入用户
            return new ResultsPack(true);
        }
        return new ResultsPack(false);
    }
}
