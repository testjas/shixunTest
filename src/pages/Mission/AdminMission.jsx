import React, { Avatar, useState, useEffect } from "react";
import Missionless from "../less/Mission.less";
import PopMenu from "./AdminMission/PopMenu";
import AddUser from "./AdminMission/AddMission";
import { Popconfirm, message, List, Skeleton, Pagination, Button } from "antd";
import {
  ProFormDateRangePicker,
  ProFormSelect,
  ProFormText,
  QueryFilter,
} from "@ant-design/pro-components";
import { GetUserList, DeleteUser } from "../../request/api";
import signIcon from "../../assets/img/signIcon.svg";
import taskIcon from "../../assets/img/taskIcon.svg"
import moment from "moment";

export default function AdminMission() {
  const [list, setList] = useState([]); //得到的数据
  const [total, setTotal] = useState(0); //总条数
  const [current, setCurrnet] = useState(1); //现在页数
  const [pageSize, setPageSize] = useState(5); //一页多少
  const [update, setUpdate] = useState(0);
  const [val, setVal] = useState(null); //设置查询条件
  const text = "你确定要删除这条数据吗？";

  const setValue = (values) => {
    setVal(values);
    getList(current, pageSize, values);
  };

  function confirm(e) {
    //确认是否删除用户
    DeleteUser({
      id: e,
    }).then((res) => {
      message.info(res.message);
      setUpdate(update + 1);
    });
  }

  const getList = (page, pageSize, ...values) => {
    //获取用户数据列表
    GetUserList({
      num: page,
      count: pageSize,
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
            name="mname"
            label="任务名称"
            rules={[{ required: true }]}
          />
          <ProFormSelect
            name="missontype"
            label="任务类型"
            showSearch
            valueEnum={{
              task: "作业",
              sign: "签到",
            }}
          />
          <ProFormSelect
            name="missonstatus"
            label="任务状态"
            showSearch
            valueEnum={{
              full: "已满",
              free: "未满"
            }}
          />
          <ProFormSelect
            name="timestatus"
            label="时间状态"
            showSearch
            valueEnum={{
              timeout:"已结束",
              intime:"已发布",
              notstart:"未开始"
            }}
          />
          <ProFormDateRangePicker name="create" label="创建时间" colSize={3} />
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
                    onConfirm={() => confirm(item.id)}
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
                    avatar={<img className="UserIcon" src={signIcon} />}
                    title={
                      <div className="title">
                        &nbsp;任务名：
                        <a className="title-text">{item.username}</a>
                        <div className="time_range">
                          任务时间范围：
                          <a>
                            {moment(item.createTime)
                              .utcOffset(0)
                              .format("YYYY-MM-DD HH:mm:ss")}
                          </a>
                          ----
                          <a>
                            {moment(item.createTime)
                              .utcOffset(0)
                              .format("YYYY-MM-DD HH:mm:ss")}
                          </a>
                        </div>
                      </div>
                    }
                    description={
                      <div className="title-auth">
                        <p className="title-authText">
                          任务发布人：{item.auth}
                        </p>
                        <p className="title-type">
                          当前任务类型：<a>{"作业类"}</a>
                        </p>
                        <p className="title-active">
                          当前任务状态：<a>{"待定"}</a>
                        </p>
                        <p className="title-active">
                          当前时间状态：<a>{"待定"}</a>
                        </p>
                      </div>
                    }
                  />
                  <div className="pwd">
                    任务创建时间：<br />
                    {moment(item.createTime)
                      .utcOffset(0)
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
