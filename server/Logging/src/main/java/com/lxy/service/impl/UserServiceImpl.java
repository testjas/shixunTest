package com.lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.entity.User;
import com.lxy.service.UserService;
import com.lxy.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author TestJas
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-05-23 15:41:16
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}




