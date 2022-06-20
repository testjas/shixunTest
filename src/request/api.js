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
export const GetMissionList = (params)=> request.post("/getMissionList",params);

//新增任务
export const AddMission = (params)=> request.post("/addMission",params);

//新增任务
export const DeleteMission = (params)=> request.post("/deleteMission",params);

//更新任务
export const UpdateMission = (params)=> request.post("/updateMission",params);

//获取登陆时间
export const GetLoginTime = (params)=> request.post("/getLoginTime",params);

//派发员操作
//获取任务列表
export const GetDisMissionList = (params)=> disRequest.post("/getDisMissionList",params);

//删除任务
export const DeleteDisMission = (params)=> disRequest.post("/deleteDisMission",params);

//更新任务
export const UpdateDisMission = (params)=> disRequest.post("/updateDisMission",params);

//添加任务
export const AddDisMission = (params)=> disRequest.post("/addDisMission",params);

//获取任务相关人员表
export const GetUserMissionInfo = (params)=> disRequest.post("/getUserMissionInfo",params);

//添加用户到一个任务中
export const AddUserMission = (params)=> disRequest.post("/addUserMission",params);

//删除一个用户任务
export const DeleteUserMission = (params)=> disRequest.post("/deleteUserMission",params);