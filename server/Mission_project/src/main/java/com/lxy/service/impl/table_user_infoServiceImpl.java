package com.lxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.entity.table_dispatcher_info;
import com.lxy.entity.table_user;
import com.lxy.entity.table_user_info;
import com.lxy.mapper.table_dispatcher_infoMapper;
import com.lxy.mapper.table_userMapper;
import com.lxy.mapper.table_user_infoMapper;
import com.lxy.service.table_user_infoService;
import com.lxy.utils.ResultsPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author TestJas
* @description 针对表【table_user_info】的数据库操作Service实现
* @createDate 2022-06-13 08:52:13
*/
@Service
public class table_user_infoServiceImpl extends ServiceImpl<table_user_infoMapper, table_user_info>
    implements table_user_infoService {
    @Autowired
    private com.lxy.mapper.table_userMapper table_userMapper;
    @Autowired
    private table_user_infoMapper table_user_infoMapper;
    @Autowired
    private com.lxy.mapper.table_dispatcher_infoMapper table_dispatcher_infoMapper;

    @Override
    public ResultsPack updateUser(int id, String name, String prename, String description) throws Exception {
        UpdateWrapper<table_user_info> uw = new UpdateWrapper();//更新操作
        if (name == null || name.equals("") || name.equals(prename)) {
            if (description == null) {
                return new ResultsPack(true, "修改成功~");
            }
            uw.set("description", description);
            uw.eq("uid", id);
            table_user_info tui = table_user_infoMapper.selectById(id);//根据id查用户信息
            table_user_infoMapper.update(tui, uw);
            return new ResultsPack(true, "已成功修改但是用户名未更新！");
        } else {
            QueryWrapper<table_user> qw = new QueryWrapper<>();
            qw.eq("username", name);
            if (table_userMapper.selectOne(qw) != null) {//判断是否存在这个用户,如果存在
                if (description==null){
                    return new ResultsPack(true, "修改失败，用户名已存在~");
                }
                uw.set("description", description);
                uw.eq("uid", id);
                table_user_info tui = table_user_infoMapper.selectById(id);//根据id查用户信息
                table_user_infoMapper.update(tui, uw);
                return new ResultsPack(true, "已成功修改但是用户名未更新！");
            }
            if (description==null){
                uw.set("username", name);
                uw.eq("uid",id);
                table_user_info tui = table_user_infoMapper.selectById(id);
                table_user_infoMapper.update(tui, uw);//更新
                return new ResultsPack(true, "修改成功~");
            }
            uw.set("username", name);
            uw.set("description",description);
            uw.eq("uid", id);
            table_user_info tui = table_user_infoMapper.selectById(id);//根据id查用户信息
            table_user_infoMapper.update(tui, uw);//更新
            return new ResultsPack(true, "成功修改信息");
        }
    }
}




