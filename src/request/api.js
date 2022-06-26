import request from "./RegRequest";
import disRequest from './DisRequest'
import userServe from './UserRequest'

//用户管理操作
//校验token
export const CheckToken = (params) => request.post("/token",params);

//获取数据
export const GetUserAuth = () => request.post("/getUserAuth");

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
//获取派发员数据
export const GetDisData = (params)=> disRequest.post("/getDisData",params);


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

//修改用户是否完成
export const UpdateUserFinish = (params)=> disRequest.post("/updateUserFinish",params);

//用户操作
//获取任务列表
export const GetUserMissionList =(params)=>userServe.post("/getUserMissionList",params);

//给用户添加任务
export const IntoMission =(params)=>userServe.post("/intoMission",params);

//获取用户的任务列表
export const GetUserMissionInfoList =(params)=>userServe.post("/getUserMissionInfo",params);

//用户完成签到
export const FinishSign =(params)=>userServe.post("/finishSign",params);

//用户完成作业
export const FinishTask =(params)=>userServe.post("/finishTask",params);

//用户数据获取
export const GetUserData =(params)=>userServe.post("/getUserData",params);