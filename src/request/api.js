import request from "./RegRequest";
import disRequest from './DisRequest'

//用户管理操作
//注册
export const RegisterApi = (params) => request.post("/register", params);

//登陆
export const LoginApi = (params) => request.post("/login", params);

//获取用户列表
export const GetUserList = (params) => request.post("/getUserList", params);

//删除用户
export const DeleteUser = (params) => request.post("/deleteUser", params);

//更新用户
export const UpdateUser = (params) => request.post("/updateUser", params);

//获取单个用户的个人信息
export const GetUser = (params) => request.post("/getUser", params);

//获取任务信息


