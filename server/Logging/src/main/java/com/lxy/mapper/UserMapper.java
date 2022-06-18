package com.lxy.mapper;

import com.lxy.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author TestJas
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-05-23 15:41:16
* @Entity com.lxy.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




