import React, { Avatar, useState, useEffect } from "react";
import Listless from "../less/Listless.less";
import PopMenu from "./UserList/PopMenu";
import AddUser from "./UserList/AddUser";
import { Popconfirm, message, List, Skeleton, Pagination, Button } from "antd";
import {
  ProFormDatePicker,
  ProFormDateRangePicker,
  ProFormSelect,
  ProFormText,
  QueryFilter,
} from "@ant-design/pro-components";
import { GetUserList, DeleteUser, UpdateUser } from "../../request/api";
import userIcon from "../../assets/img/userIcon.svg";
import moment from "moment";

export default function UserList() {
  const [list, setList] = useState([]); //得到的数据
  const [total, setTotal] = useState(0); //总条数
  const [current, setCurrnet] = useState(1); //现在页数
  const [pageSize, setPageSize] = useState(5); //一页多少
  const [update, setUpdate] = useState(0);
  const text = "你确定要删除这条数据吗？";

  // const handleOk = (e) => {//确认修改
  //   UpdateUser({
  //     id: values.id,
  //     username: e.username,
  //     date: moment(e.date).format("yyyy-MM-DD hh:mm:ss"),
  //   }).then((res) => {
  //     message.info(res.message);
  //     setUpdate(update + 1);
  //   });

  function confirm(e) {
    //确定是否需要删除
    // console.log(e)
    DeleteUser({
      id: e,
    }).then((res) => {
      message.info(res.message);
      setUpdate(update + 1);
    });
  }

  const getList = (num) => {
    //获取用户数据列表
    GetUserList({
      num,
      count: pageSize,
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

  //请求列表数据
  useEffect(() => {
    getList(current);
  }, [update]);

  const onChange = (pages) => {
    //分页触发事件
    getList(pages);
  };

  return (
    <div>
      <div className="list_search">
        <QueryFilter
          onFinish={async (values) => {
            console.log(values.name);
          }}
        >
          <ProFormText
            name="uname"
            label="用户名称"
            rules={[{ required: true }]}
          />
          <ProFormText name="belong" label="用户归属" />
          <ProFormSelect
            name="dispatcher"
            label="用户权限"
            showSearch
            valueEnum={{
              dispatcher: "派发员",
              user: "用户",
            }}
          />
          <ProFormDateRangePicker name="create" label="创建时间" colSize={3} />
        </QueryFilter>
      </div>
      <div className="add_user">
        <AddUser />
      </div>
      <div className="list_table">
        <List
          className="demo-loadmore-list"
          itemLayout="horizontal"
          dataSource={list}
          renderItem={(item) => {
            return (
              <List.Item
                actions={[
                  <PopMenu values={item} />,
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
                    avatar={<img className="UserIcon" src={userIcon} />}
                    title={
                      <div className="title">
                        &nbsp;用户名：
                        <a className="title-text">{item.username}</a>
                      </div>
                    }
                    description={
                      <div className="title-auth">
                        <p className="title-authText">用户归属：{item.auth}</p>
                      </div>
                    }
                  />
                  <div className="pwd">
                    最近一次登录/修改时间:
                    {moment(item.date).format("yyyy-MM-DD hh:mm:ss")}
                  </div>
                </Skeleton>
              </List.Item>
            );
          }}
        />
        <Pagination
          style={{ float: "right", marginTop: "20px" }}
          onChange={onChange}
          current={current}
          pageSize={pageSize}
          total={total}
        />
      </div>
    </div>
  );
}
