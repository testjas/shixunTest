import React, { Avatar, useState, useEffect } from "react";
import Listless from "../less/Listless.less";
import PopMenu from "./DispatcherList/PopMenu";
import AddUser from "./DispatcherList/AddUser";
import { Popconfirm, message, List, Skeleton, Pagination, Button } from "antd";
import {
  ProFormDateTimeRangePicker,
  ProFormSelect,
  ProFormText,
  QueryFilter,
} from "@ant-design/pro-components";
import { GetUserMissionInfo, DeleteUserMission } from "../../request/api";
import userIcon from "../../assets/img/userIcon.svg";
import moment from "moment";

export default function DispatcherList() {
  const [list, setList] = useState([]); //得到的数据
  const [total, setTotal] = useState(0); //总条数
  const [current, setCurrnet] = useState(1); //现在页数
  const [pageSize, setPageSize] = useState(5); //一页多少
  const [update, setUpdate] = useState(0);
  const [val,setVal]=useState(null);//设置查询条件
  const text = "你确定要删除这条数据吗？";

  const setValue=(values)=>{
    setVal(values);
    getList(current, pageSize,values);
  }

  function pdFinish(e) {//判定是否完成
    if (e == 1) {
      return "已完成";
    } else {
      return "未完成";
    }
  }

  function pdFinishTime(e) {//判定是否完成时间
    if (e === null) {
      return "还未完成哦";
    } else {
      return e;
    }
  }

  function confirm(e,t) {
    //确认是否删除任务
    DeleteUserMission({
      uid:e,
      mid:t,
      disName:localStorage.getItem("username")
    }).then((res)=>{
      if(res.flag===true){
        message.success(res.message)
        setUpdate(update+1)
      }else{
        message.error(res.message)
        setUpdate(update+1)
      }
    })
  }

  const getList = (page,pageSize,...values) => {
    //获取用户数据列表
    GetUserMissionInfo({
      num: page,
      count: pageSize,
      username:localStorage.getItem("username"),
      values
    }).then((res) => {
      if (res.flag === true) {
        let { arr, num, count, total } = res.data;
        setList(arr);
        setCurrnet(num);
        setPageSize(count);
        setTotal(total);
      }
    });
  };

  function setUp(e) {
    //子组件更新时调用此方法刷新父组件
    setUpdate(update + e);
  }

  //请求列表数据
  useEffect(() => {
    getList(current,pageSize,val);
  }, [update]);

  const onChange = (page, pageSize) => {
    setPageSize(pageSize);
    //分页触发事件
    getList(page, pageSize,val);
  };

  return (
    <div>
      <div className="list_search">
        <QueryFilter
          onFinish={async (values) => {
            setValue(values);
          }}
          onReset={async () => {
            setVal(null);
            getList(1, pageSize);
          }}
        >
          <ProFormText
            name="uname"
            label="用户名称"
          />
          <ProFormText
            name="missionName"
            label="任务名称"
          />
          <ProFormSelect
            name="isFinish"
            label="是否完成"
            showSearch
            valueEnum={{
              1: "完成",
              0: "未完成",
            }}
          />
          <ProFormDateTimeRangePicker name="joinTime" label="加入时间" colSize={3} />
          <ProFormDateTimeRangePicker name="finishTime" label="完成时间" colSize={3} />
        </QueryFilter>
      </div>
      <div className="add_user">
        <AddUser setUp={setUp} />
      </div>
      <div className="llist_table">
        <List
          className="demo-loadmore-list"
          itemLayout="horizontal"
          dataSource={list}
          renderItem={(item) => {
            return (
              <List.Item
                actions={[
                  <PopMenu setUp={setUp} values={item} />,
                  <Popconfirm //弹窗确认
                    placement="topLeft"
                    title={text}
                    onConfirm={() => confirm(item.uid,item.mid)}
                    okText="确认"
                    cancelText="取消"
                  >
                    <Button type="danger" key="list-loadmore-more">
                      删除
                    </Button>
                  </Popconfirm>,
                ]}
              >
                <Skeleton loading={false} active>
                  <List.Item.Meta
                    avatar={<img className="UserIcon" src={userIcon} />}
                    title={
                      <div className="title">
                        &nbsp;用户名：
                        <a className="title-text">{item.username}</a>
                        <div className="create_time">
                          用户加入时间：
                          <a>
                            {moment(item.joinTime)
                              .utcOffset(8)
                              .format("YYYY-MM-DD HH:mm:ss")}
                          </a>
                          
                        </div>
                      </div>
                    }
                    description={
                      <div className="title-auth">
                        <p className="title-authText">归属任务：{item.missionName}</p>
                        <p className="title-active">是否完成：{pdFinish(item.finish)}</p>
                      </div>
                    }
                  />
                  <div className="pwd">
                    完成时间: <br />
                    {pdFinishTime(item.finishTime)}
                  </div>
                </Skeleton>
              </List.Item>
            );
          }}
        />
        <Pagination
          style={{ float: "right", marginTop: "20px" }}
          showSizeChanger
          pageSizeOptions={[5, 10, 20, 50, 100]}
          onChange={onChange}
          current={current}
          pageSize={pageSize}
          total={total}
        />
      </div>
    </div>
  );
}
