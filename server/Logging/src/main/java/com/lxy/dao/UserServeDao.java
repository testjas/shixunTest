package com.lxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxy.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserServeDao extends BaseMapper<Admin> {
}
