package com.lxy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxy.entity.*;
import com.lxy.mapper.*;
import com.lxy.service.table_mission_infoService;
import com.lxy.utils.ResultsPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @author TestJas
* @description 针对表【table_mission_info】的数据库操作Service实现
* @createDate 2022-06-13 08:52:13
*/
@Service
public class table_mission_infoServiceImpl extends ServiceImpl<table_mission_infoMapper, table_mission_info>
    implements table_mission_infoService {

    @Autowired
    private table_userMapper table_userMapper;
    @Autowired
    private table_user_infoMapper table_user_infoMapper;
    @Autowired
    private table_dispatcher_infoMapper table_dispatcher_infoMapper;
    @Autowired
    private table_mission_infoMapper table_mission_infoMapper;
    @Autowired
    private table_user_missionMapper table_user_missionMapper;
    @Autowired
    private mission_user_infoMapper mission_user_infoMapper;

    @Override
    public ResultsPack getMissionList(int num, int count, JSONObject search) throws Exception {
        IPage<table_mission_info> page = new Page<>(num, count);//分页,true表示携带返回总条数数据
        QueryWrapper qw = new QueryWrapper();//根据条件查询
        //查询要传走的字段
        qw.select("mid","mission_name","description","user_num","joined_num","create_time","start_time","end_time","status","finish_status","time_status","belong","mission_type","isdelete");
        if (search!=null){
            if (!search.isEmpty()){
                String mname=search.getString("mname");
                if (mname!=null){
                    qw.like("mission_name",mname);//任务名条件查询
                }
                String disname=search.getString("disname");
                if (disname!=null){
                    qw.like("belong",disname);
                }
                String timestatus=search.getString("timestatus");
                if (timestatus!=null){
                    qw.eq("time_status",timestatus);//任务类型条件查询
                }
                String missiontype=search.getString("missiontype");
                if (missiontype!=null){
                    qw.eq("mission_type",missiontype);//任务类型条件查询
                }
                String time_status=search.getString("timestatus");
                if (time_status!=null){
                    qw.eq("time_status",time_status);//任务时间状态查询
                }
                String start="";
                String end="";
                if (search.getJSONArray("create")!=null){//判断是否有日期
                    start= (String) search.getJSONArray("create").get(0);
                    end=(String) search.getJSONArray("create").get(1);
                    qw.between("create_time",start,end);
                }
                String status=search.getString("status");
                if (status!=null){
                    qw.eq("isdelete",Integer.parseInt(status));
                }
            }
        }
        IPage<table_mission_info> list = table_mission_infoMapper.selectPage(page, qw);//获取用户
        if (list == null) {
            return new ResultsPack(false, "显示数据出错");
        }
        JSONObject jsonObject = new JSONObject();//把数据封装在json串中
        jsonObject.put("arr", list.getRecords());
        jsonObject.put("total", list.getTotal());
        jsonObject.put("num", list.getCurrent());
        jsonObject.put("count", list.getSize());
        return new ResultsPack(true, jsonObject, "查询数据成功");
    }

    @Override
    public void updateMissionInfo() throws Exception {
        table_user_missionMapper.deleteUM();//删除要删除的用户
        table_mission_infoMapper.deleteMission();//删除要删除的任务
        table_mission_infoMapper.updateTime();//更新任务的是否满员状态
        table_mission_infoMapper.updateStatus();//更新任务的时间状态
    }

    @Override
    public ResultsPack addMission(table_mission_info table_mission_info) throws Exception {
        QueryWrapper<table_mission_info> qwm=new QueryWrapper<>();
        qwm.eq("mission_name",table_mission_info.getMissionName());
        QueryWrapper<table_user> qw = new QueryWrapper<>();
        qw.eq("username", table_mission_info.getBelong());
        qw.eq("auth","dispatcher");
        qw.ne("isdelete",1);
        if (table_mission_infoMapper.selectOne(qwm)!=null){//判断任务名是否冲突
            return new ResultsPack(false,"插入错误，不可以和别的任务名冲突哦~");
        }
        if (table_userMapper.selectOne(qw) != null) {//判断是否存在这个用户
            table_mission_infoMapper.insert(table_mission_info);//插入插入
            return new ResultsPack(true, "添加成功");
        } else {
//            PasswordEncryption ps=new PasswordEncryption();//密码进入数据库前进行加密处理
//            ps.setPs(user.getPassword(), user.getId());
//            user.setPassword(ps.getPs());
            return new ResultsPack(false, "该派发员不存在哦~请重试");
        }
    }

    @Override
    public ResultsPack deleteMission(int mid) throws Exception {
        UpdateWrapper<table_mission_info> utmi=new UpdateWrapper<>();
        utmi.set("isdelete",1);
        utmi.eq("mid",mid);
        QueryWrapper<table_mission_info> qtmi=new QueryWrapper<>();
        qtmi.eq("mid",mid);
        table_mission_info tmi=table_mission_infoMapper.selectById(mid);
        System.out.println(tmi);
        int a=table_mission_infoMapper.update(tmi,utmi);
        System.out.println(a);
        if (a != 1){
            return new ResultsPack(false,"哦！好像发生了什么错误！请重试~");
        }else{
            return new ResultsPack(true,"删除成功");
        }
    }

    @Override
    public ResultsPack updateMission(JSONObject jsonObject) throws Exception {
        UpdateWrapper<table_mission_info> utmi=new UpdateWrapper<>();
        String belong=jsonObject.getString("belong");//用户名
        utmi.eq("mid",jsonObject.getInteger("mid"));
        if (belong!=null){
            QueryWrapper<table_user> qw = new QueryWrapper<>();
            qw.eq("username", belong);
            qw.eq("auth","dispatcher");
            if (table_userMapper.selectOne(qw)!=null){//判断更新的人名存在且权限是派发员
                utmi.set("belong",belong);
            }else{
                return new ResultsPack(false,"请检查修改后的用户名是否存在或者有权限");
            }
        }
        String mname=jsonObject.getString("mname");//任务名
        if (mname!=null){
            utmi.set("mission_name",mname);
        }
        String description=jsonObject.getString("description");//简介
        if (description!=null){
            utmi.set("description",description);
        }
        String missionStatus=jsonObject.getString("missionStatus");//状态
        if (missionStatus!=null){
            if (missionStatus.equals("正常")){
                utmi.set("isdelete",0);
            }else if(missionStatus.equals("中止")){
                utmi.set("isdelete",2);
            }
        }
        Integer unum=jsonObject.getInteger("unum");//人数
        if (unum!=null){
            utmi.set("user_num",unum);
        }
        String missionType=jsonObject.getString("missionType");//类型
        if (missionType!=null){
            utmi.set("mission_type",missionType);
        }
        String start="";
        String end="";
        if (jsonObject.getJSONArray("missionTime")!=null){//判断是否有日期
            start= (String) jsonObject.getJSONArray("missionTime").get(0);
            end=(String) jsonObject.getJSONArray("missionTime").get(1);
            utmi.set("start_time",start);
            utmi.set("end_time",end);
        }
        table_mission_info tmi=table_mission_infoMapper.selectById(jsonObject.getInteger("mid"));
        int a=table_mission_infoMapper.update(tmi,utmi);
        if (a!=0){
            return new ResultsPack(true,"更新成功");
        }
        return new ResultsPack(false,"发生未知错误了，请重试");
    }

    //派发员
    @Override//获取任务列表
    public ResultsPack getDisMissionList(int num, int count,String disname ,JSONObject search) throws Exception {
        IPage<table_mission_info> page = new Page<>(num, count);//分页,true表示携带返回总条数数据
        QueryWrapper qw = new QueryWrapper();//根据条件查询
        //查询要传走的字段
        qw.select("mid","mission_name","description","user_num","joined_num","create_time","start_time","end_time","status","finish_status","time_status","mission_type","isdelete");
        qw.eq("belong",disname);
        if (search!=null){
            if (!search.isEmpty()){
                String mname=search.getString("mname");
                if (mname!=null){
                    qw.like("mission_name",mname);//任务名条件查询
                }
                String timestatus=search.getString("timestatus");
                if (timestatus!=null){
                    qw.eq("time_status",timestatus);//任务类型条件查询
                }
                String missiontype=search.getString("missiontype");
                if (missiontype!=null){
                    qw.eq("mission_type",missiontype);//任务类型条件查询
                }
                String time_status=search.getString("timestatus");
                if (time_status!=null){
                    qw.eq("time_status",time_status);//任务时间状态查询
                }
                String start="";
                String end="";
                if (search.getJSONArray("create")!=null){//判断是否有日期
                    start= (String) search.getJSONArray("create").get(0);
                    end=(String) search.getJSONArray("create").get(1);
                    qw.between("create_time",start,end);
                }
                String status=search.getString("status");
                if (status!=null){
                    qw.eq("isdelete",Integer.parseInt(status));
                }
            }
        }
        IPage<table_mission_info> list = table_mission_infoMapper.selectPage(page, qw);//获取用户
        if (list == null) {
            return new ResultsPack(false, "显示数据出错");
        }
        JSONObject jsonObject = new JSONObject();//把数据封装在json串中
        jsonObject.put("arr", list.getRecords());
        jsonObject.put("total", list.getTotal());
        jsonObject.put("num", list.getCurrent());
        jsonObject.put("count", list.getSize());
        return new ResultsPack(true, jsonObject, "查询数据成功");
    }

    @Override//删除任务
    public ResultsPack deleteDisMission(int mid) throws Exception {
        UpdateWrapper<table_mission_info> utmi=new UpdateWrapper<>();
        utmi.set("isdelete",1);
        utmi.eq("mid",mid);
        QueryWrapper<table_mission_info> qtmi=new QueryWrapper<>();
        qtmi.eq("mid",mid);
        table_mission_info tmi=table_mission_infoMapper.selectById(mid);
        int a=table_mission_infoMapper.update(tmi,utmi);
        if (a != 1){
            return new ResultsPack(false,"哦！好像发生了什么错误！请重试~");
        }else{
            return new ResultsPack(true,"删除成功");
        }
    }

    @Override//更新任务
    public ResultsPack updateDisMission(JSONObject jsonObject) throws Exception {
        UpdateWrapper<table_mission_info> utmi=new UpdateWrapper<>();
        utmi.eq("mid",jsonObject.getInteger("mid"));
        String mname=jsonObject.getString("mname");//任务名
        if (mname!=null){
            utmi.set("mission_name",mname);
        }
        String description=jsonObject.getString("description");//简介
        if (description!=null){
            utmi.set("description",description);
        }
        String missionStatus=jsonObject.getString("missionStatus");//状态
        if (missionStatus!=null){
            if (missionStatus.equals("正常")){
                utmi.set("isdelete",0);
            }else if(missionStatus.equals("中止")){
                utmi.set("isdelete",2);
            }
        }
        Integer unum=jsonObject.getInteger("unum");//人数
        if (unum!=null){
            utmi.set("user_num",unum);
        }
        String missionType=jsonObject.getString("missionType");//类型
        if (missionType!=null){
            utmi.set("mission_type",missionType);
        }
        String start="";
        String end="";
        if (jsonObject.getJSONArray("missionTime")!=null){//判断是否有日期
            start= (String) jsonObject.getJSONArray("missionTime").get(0);
            end=(String) jsonObject.getJSONArray("missionTime").get(1);
            utmi.set("start_time",start);
            utmi.set("end_time",end);
        }
        table_mission_info tmi=table_mission_infoMapper.selectById(jsonObject.getInteger("mid"));
        int a=table_mission_infoMapper.update(tmi,utmi);
        if (a!=0){
            return new ResultsPack(true,"更新成功");
        }
        return new ResultsPack(false,"发生未知错误了，请重试");
    }

    @Override
    public ResultsPack addDisMission(table_mission_info table_mission_info) throws Exception {
        QueryWrapper<table_mission_info> qwm=new QueryWrapper<>();
        qwm.eq("mission_name",table_mission_info.getMissionName());
        if (table_mission_infoMapper.selectOne(qwm)!=null){//判断任务名是否冲突
            return new ResultsPack(false,"插入错误，不可以和别的任务名冲突哦~");
        }
        int a=table_mission_infoMapper.insert(table_mission_info);//插入任务
        if (a!=0) {//判断是插入成功
            return new ResultsPack(true, "添加成功");
        } else {
            return new ResultsPack(false, "好像出错了~请重试");
        }
    }

    @Override
    public ResultsPack userMissionInfo(int num, int count,String disname ,JSONObject search) throws Exception {
        int total=0;
        QueryWrapper qw = new QueryWrapper();//根据条件查询
        qw.eq("belong",disname);
        ArrayList<mission_user_info> list=null;
        if (search!=null){
            if (!search.isEmpty()){
                String userName=search.getString("uname");
                String missionName=search.getString("missionName");
                Integer isFinish=search.getInteger("isFinish");
                String startJoin=null;
                String endJoin=null;
                if (search.getJSONArray("joinTime")!=null){//判断是否有日期
                    startJoin= (String) search.getJSONArray("joinTime").get(0);
                    endJoin=(String) search.getJSONArray("joinTime").get(1);
                }
                String startFinish=null;
                String endFinish=null;
                if (search.getJSONArray("finishTime")!=null){//判断是否有日期
                    startJoin= (String) search.getJSONArray("finishTime").get(0);
                    endJoin=(String) search.getJSONArray("finishTime").get(1);
                }
                list=table_mission_infoMapper.userMissionInfo(userName,missionName,isFinish,startJoin,endJoin,startFinish,endFinish,disname,null,null);
                total=list.size();
                list=table_mission_infoMapper.userMissionInfo(userName,missionName,isFinish,startJoin,endJoin,startFinish,endFinish,disname,(num-1)*count,count);
            }
        }else{
            list=table_mission_infoMapper.userMissionInfo(null,null,null,null,null,null,null,disname,null,null);
            total=list.size();
            list=table_mission_infoMapper.userMissionInfo(null,null,null,null,null,null,null,disname,(num-1)*count,count);
        }
        if (list == null) {
            return new ResultsPack(false, "显示数据出错");
        }
        JSONObject jsonObject = new JSONObject();//把数据封装在json串中
        jsonObject.put("arr", list);
        jsonObject.put("total", total);
        jsonObject.put("num", num);
        jsonObject.put("count",count);
        return new ResultsPack(true, jsonObject, "查询数据成功");
    }

    @Override
    public ResultsPack addUserMission(JSONObject jsonObject) throws Exception{
        String missionName=jsonObject.getString("missionName");
        String disName=jsonObject.getString("disName");
        String username=jsonObject.getString("username");
        QueryWrapper<table_mission_info> tmi=new QueryWrapper<>();
        tmi.eq("mission_name",missionName);
        tmi.eq("belong",disName);
        table_mission_info table_mission_info=table_mission_infoMapper.selectOne(tmi);
        if (table_mission_info!=null){//判断是否存在这个任务
            if (table_mission_info.getJoinedNum()>=table_mission_info.getUserNum()){
                return new ResultsPack(false,"该任务已经满员无法再添加用户，请修改用户容量~");
            }
            QueryWrapper<table_user_info> tui=new QueryWrapper<>();
            tui.eq("username",username);
            table_user_info table_user_info=table_user_infoMapper.selectOne(tui);
            if (table_user_info!=null){//判断是否有这个用户
                QueryWrapper<table_user_mission> um=new QueryWrapper<>();
                um.eq("uid",table_user_info.getUid());
                um.eq("mid",table_mission_info.getMid());
                table_user_mission tum=table_user_missionMapper.selectOne(um);
                if (tum!=null){
                    return new ResultsPack(false,"该用户已经加入过这个任务咯，不能再加入啦~");
                }else{
                    table_user_mission table_user_mission=new table_user_mission();
                    table_user_mission.setMid(table_mission_info.getMid());
                    table_user_mission.setUid(table_user_info.getUid());
                    table_user_mission.setJoinTime(new Date());
                    int a=table_user_missionMapper.insert(table_user_mission);
                    if (a>0){
                        return new ResultsPack(true,"成功将用户:"+username+" ,加入:"+missionName+" 任务");
                    }else{
                        return new ResultsPack(false,"发生了不知道什么问题，请重试一下哦");
                    }

                }
            }else {
                return new ResultsPack(false,"任务有，但是没有该用户哦~");
            }
        }else {
            return new ResultsPack(false,"不存在该任务，请重试~");
        }
    }

    @Override
    public ResultsPack updateUserFinish(JSONObject jsonObject) throws Exception {
        Integer uid=jsonObject.getInteger("uid");
        Integer mid=jsonObject.getInteger("mid");
        Integer finish=jsonObject.getInteger("finish");
        if (finish!=null){
            UpdateWrapper<table_user_mission> tum=new UpdateWrapper<>();
            if (finish==1){
                tum.eq("mid",mid).eq("uid",uid).set("finish",finish).set("finish_time",new Date());
            }else if(finish==0){
                tum.eq("mid",mid).eq("uid",uid).set("finish",finish).set("finish_time",null);
            }else{
                return new ResultsPack(false,"发生未知错误，请重试");
            }
            QueryWrapper<table_user_mission> um=new QueryWrapper<>();
            um.eq("mid",mid);
            um.eq("uid",uid);
            table_user_mission table_user_mission=table_user_missionMapper.selectOne(um);
            int a=table_user_missionMapper.update(table_user_mission,tum);
            if (a>0){
                return new ResultsPack(true,"修改成功咯~");
            }else{
                return new ResultsPack(false,"修改失败请重试");
            }
        }else{
            return new ResultsPack(false,"你好像什么都没改诶~");
        }
    }

    //用户
    @Override
    public ResultsPack getUserMissionList(int num, int count, String username, JSONObject search) throws Exception {
        IPage<table_mission_info> page = new Page<>(num, count);//分页,true表示携带返回总条数数据
        QueryWrapper<table_mission_info> qw = new QueryWrapper();//根据条件查询
        //查询要传走的字段
        qw.select("mid","mission_name","description","user_num","joined_num","create_time","start_time","end_time","status","finish_status","time_status","mission_type","belong","isdelete");
        if (search!=null){
            if (!search.isEmpty()){
                String mname=search.getString("mname");
                if (mname!=null){
                    qw.like("mission_name",mname);//任务名条件查询
                }
                Integer canJoin=search.getInteger("canJoin");
                if (canJoin!=null){
                    if (canJoin==1){
                        qw.and(wrapper->wrapper.eq("isdelete",0).eq("time_status","已发布").eq("status","未满"));
                    }else{
                        qw.and(wrapper->wrapper.eq("isdelete",2).or().ne("time_status","已发布").or().eq("status","已满"));
                    }
                }
                String timestatus=search.getString("timestatus");
                if (timestatus!=null){
                    qw.eq("time_status",timestatus);//任务类型条件查询
                }
                String missiontype=search.getString("missiontype");
                if (missiontype!=null){
                    qw.eq("mission_type",missiontype);//任务类型条件查询
                }
                String time_status=search.getString("timestatus");
                if (time_status!=null){
                    qw.eq("time_status",time_status);//任务时间状态查询
                }
                String start="";
                String end="";
                if (search.getJSONArray("create")!=null){//判断是否有日期
                    start= (String) search.getJSONArray("create").get(0);
                    end=(String) search.getJSONArray("create").get(1);
                    qw.between("create_time",start,end);
                }
                String status=search.getString("status");
                if (status!=null){
                    qw.eq("isdelete",Integer.parseInt(status));
                }
            }
        }
        IPage<table_mission_info> list = table_mission_infoMapper.selectPage(page,qw);//获取用户
        if (list == null) {
            return new ResultsPack(false, "显示数据出错");
        }
        JSONObject jsonObject = new JSONObject();//把数据封装在json串中
        jsonObject.put("arr", list.getRecords());
        jsonObject.put("total", list.getTotal());
        jsonObject.put("num", list.getCurrent());
        jsonObject.put("count", list.getSize());
        return new ResultsPack(true, jsonObject, "查询数据成功");
    }

    @Override
    public ResultsPack getUserMissionInfo(int num, int count, String username, JSONObject search) throws Exception {
        IPage<user_mission_list_info> page = new Page<>(num, count);//分页,true表示携带返回总条数数据
        Map<String,Object> map=new HashMap<>();
        map.put("username",username);
        if (search!=null){
            if (!search.isEmpty()){
                String mname=search.getString("mname");
                if (mname!=null){
                    map.put("missionName",mname);//任务名条件查询
                }
                String timestatus=search.getString("timestatus");
                if (timestatus!=null){
                    map.put("timeStatus",timestatus);//任务类型条件查询
                }
                String missiontype=search.getString("missiontype");
                if (missiontype!=null){
                    map.put("missionType",missiontype);//任务类型条件查询
                }
                Integer missionStatus=search.getInteger("missionstatus");
                if (missionStatus!=null){
                    map.put("missionStatus",missionStatus);//任务是否完成
                }
                String start="";
                String end="";
                if (search.getJSONArray("join")!=null){//判断是否有日期
                    start= (String) search.getJSONArray("join").get(0);
                    end=(String) search.getJSONArray("join").get(1);
                    map.put("start",start);
                    map.put("end",end);
                }
                Integer status=search.getInteger("status");
                if (status!=null){
                    map.put("isdelete",status);
                }

                String full=search.getString("finish");
                if (full!=null){
                    map.put("finish",full);
                }
            }
        }
        IPage<user_mission_list_info> list = table_mission_infoMapper.userMissionInfoList(map,page);//获取用户
        if (list == null) {
            return new ResultsPack(false, "显示数据出错");
        }
        JSONObject jsonObject = new JSONObject();//把数据封装在json串中
        jsonObject.put("arr", list.getRecords());
        jsonObject.put("total", list.getTotal());
        jsonObject.put("num", list.getCurrent());
        jsonObject.put("count", list.getSize());
        return new ResultsPack(true, jsonObject, "查询数据成功");
    }


}




