import React, { Avatar, useState, useEffect } from "react";
import Missionless from "../less/Mission.less";
import PopMenu from "./UserList/PopMenu";
import TaskFinshMenu from "./UserList/TaskFinshMenu";
import { Popconfirm, message, List, Skeleton, Pagination, Button } from "antd";
import {
  ProFormDateTimeRangePicker,
  ProFormSelect,
  ProFormText,
  QueryFilter,
} from "@ant-design/pro-components";
import { FinishSign, GetUserMissionInfoList } from "../../request/api";
import signIcon from "../../assets/img/signIcon.svg";
import taskIcon from "../../assets/img/taskIcon.svg";
import moment from "moment";

export default function UserList() {
  const [list, setList] = useState([]); //得到的数据
  const [total, setTotal] = useState(0); //总条数
  const [current, setCurrnet] = useState(1); //现在页数
  const [pageSize, setPageSize] = useState(5); //一页多少
  const [update, setUpdate] = useState(0);
  const [val, setVal] = useState(); //设置查询条件
  const text = "你确定要进行任务吗？";

  function join(e) {
    if (
      e.isdelete === 2 ||
      e.timeStatus === "已结束" ||
      e.timeStatus === "未开始" ||
      e.finish ===1
    ) {
      return 1;
    } else {
      return 0;
    }
  }

  function finish(e) {
    if (e) {
      return moment(e).utcOffset(8).format("YYYY-MM-DD HH:mm:ss");
    } else {
      return "还没有完成哦~";
    }
  }

  const setValue = (values) => {
    setVal(values);
    getList(current, pageSize, values);
  };

  function canJoin(e) {
    //判断是否可加入
    if (e === 1) {
      return "已完成";
    } else {
      return "未完成";
    }
  }

  function pdStatus(e) {
    //判断是否中止
    if (e === 0) {
      return "正常";
    } else {
      return "中止";
    }
  }

  function chooseButton(e){//选择任务完成的按钮
    if (e.missionType === "作业") {
      return <TaskFinshMenu values={e} setUp={setUp} />;
    } else {
     return <Button
        size="small"
        disabled={join(e)}
        type="primary"
        key="list-loadmore-more"
      >
        完成任务
      </Button>;
    }
  };

  function confirm(e) {
    console.log(e);
    if (e.missionType === "签到") {
      console.log("签到");
    } else if (e.missionType === "作业") {
      console.log("作业");
    }
    //确认是否删除任务
    FinishSign({
      mid: e.mid,
      uid: e.uid
    }).then((res) => {
      if(res.flag===true){
        message.success(res.message);
      }else{
        message.error(res.message);
      }
      setUpdate(update + 1);
    });
  }

  const getList = (page, pageSize, ...values) => {
    //获取任务数据列表
    GetUserMissionInfoList({
      num: page,
      count: pageSize,
      username: localStorage.getItem("username"),
      values,
    }).then((res) => {
      console.log(res.data)
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
    getList(current, pageSize, val);
  }, [update]);

  const onChange = (page, pageSize) => {
    setPageSize(pageSize);
    //分页触发事件
    getList(page, pageSize, val);
  };

  return (
    <div className="total">
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
          <ProFormText name="mname" label="任务名称" />
          <ProFormSelect
            name="timestatus"
            label="时间状态"
            showSearch
            valueEnum={{
              已结束: "已结束",
              已发布: "已发布",
              未开始: "未开始",
            }}
          />
          <ProFormSelect
            name="missiontype"
            label="任务类型"
            showSearch
            valueEnum={{
              作业: "作业",
              签到: "签到",
            }}
          />
          <ProFormSelect
            name="missionstatus"
            label="任务状态"
            showSearch
            valueEnum={{
              1: "已完成",
              0: "未完成",
            }}
          />
          <ProFormSelect
            name="finish"
            label="任务状态"
            showSearch
            valueEnum={{
              已满: "已满",
              未满: "未满",
            }}
          />
          <ProFormSelect
            name="status"
            label="状态"
            showSearch
            valueEnum={{
              0: "正常",
              2: "中止",
            }}
          />
          <ProFormDateTimeRangePicker
            name="join"
            label="加入时间"
            colSize={3}
          />
        </QueryFilter>
      </div>
      <div className="blank"></div>
      <div className="mlist_table">
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
                    onConfirm={() => confirm(item)}
                    okText="确认"
                    cancelText="取消"
                    disabled={join(item)}
                  >
                    {chooseButton(item)}
                  </Popconfirm>,
                ]}
              >
                <Skeleton loading={false} active>
                  <List.Item.Meta
                    avatar={
                      <img
                        className="MissionIcon"
                        src={item.missionType === "签到" ? signIcon : taskIcon}
                      />
                    }
                    title={
                      <div className="title">
                        &nbsp;任务名：
                        <a className="title-text">{item.missionName}</a>
                        <div className="time_range">
                          任务时间范围：
                          <a>
                            {moment(item.startTime)
                              .utcOffset(8)
                              .format("YYYY-MM-DD HH:mm:ss")}
                          </a>
                          ----
                          <a>
                            {moment(item.endTime)
                              .utcOffset(8)
                              .format("YYYY-MM-DD HH:mm:ss")}
                          </a>
                        </div>
                        <div className="status">
                          状态：<a>{pdStatus(item.isdelete)}</a>
                        </div>
                      </div>
                    }
                    description={
                      <div className="title-auth">
                        <p className="title-type">
                          任务归属：<a>{item.belong}</a>
                        </p>
                        <p className="title-type">
                          当前任务类型：<a>{item.missionType}</a>
                        </p>
                        <p className="title-active">
                          当前任务状态：<a>{item.status}</a>
                        </p>
                        <p className="title-active">
                          当前时间状态：<a>{item.timeStatus}</a>
                        </p>
                        <p className="title-finish">
                          参与人数：
                          <a>
                            {item.userNum}/{item.joinedNum}
                          </a>
                        </p>
                        <p className="title-active">
                          是否完成：<a>{canJoin(item.finish)}</a>
                        </p>
                      </div>
                    }
                  />
                  <div className="pwd">
                    任务完成时间：
                    <br />
                    {finish(item.finishTime)}
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
