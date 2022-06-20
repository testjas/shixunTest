import React, { Avatar, useState, useEffect } from "react";
import Missionless from "../less/Mission.less";
import PopMenu from "./DispatcherMission/PopMenu";
import AddUser from "./DispatcherMission/AddMission";
import { Popconfirm, message, List, Skeleton, Pagination, Button } from "antd";
import {
  ProFormDateTimeRangePicker,
  ProFormSelect,
  ProFormText,
  QueryFilter,
} from "@ant-design/pro-components";
import {  DeleteDisMission,GetDisMissionList } from "../../request/api";
import signIcon from "../../assets/img/signIcon.svg";
import taskIcon from "../../assets/img/taskIcon.svg"
import moment from "moment";

export default function DispatcherMission() {
  const [list, setList] = useState([]); //得到的数据
  const [total, setTotal] = useState(0); //总条数
  const [current, setCurrnet] = useState(1); //现在页数
  const [pageSize, setPageSize] = useState(5); //一页多少
  const [update, setUpdate] = useState(0);
  const [val, setVal] = useState(null); //设置查询条件
  const text = "你确定要删除这条任务吗？";

  const setValue = (values) => {
    setVal(values);
    getList(current, pageSize, values);
  };

  function pdStatus(e){//判断是否中止
    if(e===0){
      return "正常";
    }else{
      return "中止";
    }
  }

  function confirm(e) {
    console.log(e)
    //确认是否删除任务
    DeleteDisMission({
      mid: e,
      username:localStorage.getItem("username")
    }).then((res) => {
      message.info(res.message);
      setUpdate(update + 1);
    });
  }

  const getList = (page, pageSize, ...values) => {
    //获取任务数据列表
    GetDisMissionList({
      num: page,
      count: pageSize,
      username:localStorage.getItem("username"),
      values,
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
          <ProFormText
            name="mname"
            label="任务名称"
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
              已满: "已满",
              未满: "未满"
            }}
          />
          <ProFormSelect
            name="timestatus"
            label="时间状态"
            showSearch
            valueEnum={{
              已结束:"已结束",
              已发布:"已发布",
              未开始:"未开始"
            }}
          />
          <ProFormSelect
            name="status"
            label="状态"
            showSearch
            valueEnum={{
              0:"正常",
              2:"中止",
            }}
          />
          <ProFormDateTimeRangePicker name="create" label="创建时间" colSize={3} />
        </QueryFilter>
      </div>
      <div className="add_user">
        <AddUser setUp={setUp} />
      </div>
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
                    onConfirm={() => confirm(item.mid)}
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
                    avatar={<img className="MissionIcon" src={item.missionType==="签到"? signIcon:taskIcon} />}
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
                        <div className="status">状态：<a>{pdStatus(item.isdelete)}</a></div>
                      </div>
                    }
                    description={
                      <div className="title-auth">
                        <p className="title-type">
                          当前任务类型：<a>{item.missionType  }</a>
                        </p>
                        <p className="title-active">
                          当前任务状态：<a>{item.status}</a>
                        </p>
                        <p className="title-active">
                          当前时间状态：<a>{item.timeStatus}</a>
                        </p>
                        <p className="title-finish">
                          参与人数：<a>{item.userNum}/{item.joinedNum}</a>
                        </p>
                        <p className="title-active">
                          已完成：<a>{item.finishStatus}</a>
                        </p>
                      </div>
                    }
                  />
                  <div className="pwd">
                    任务创建时间：<br />
                    {moment(item.createTime)
                      .utcOffset(8)
                      .format("YYYY-MM-DD HH:mm:ss")}
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
