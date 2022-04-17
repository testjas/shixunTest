package com.lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.entity.Admin;
import com.lxy.utils.ResultsPack;

public interface UserServeService extends IService<Admin> {
    public ResultsPack logging(Admin admin);
    public ResultsPack register(Admin admin);
}
