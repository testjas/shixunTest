package com.lxy.service;

import com.lxy.entity.table_dispatcher_info;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxy.utils.ResultsPack;

/**
* @author TestJas
* @description 针对表【table_dispatcher_info】的数据库操作Service
* @createDate 2022-06-13 08:52:13
*/
public interface table_dispatcher_infoService extends IService<table_dispatcher_info> {
    public ResultsPack updateUser(int id, String name, String prename, String description) throws Exception;
}
