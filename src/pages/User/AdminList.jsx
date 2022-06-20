import React, { Avatar, useState, useEffect } from "react";
import Listless from "../less/Listless.less";
import PopMenu from "./AdminList/PopMenu";
import AddUser from "./AdminList/AddUser";
import { Popconfirm, message, List, Skeleton, Pagination, Button } from "antd";
import {
  ProFormDateTimeRangePicker,
  ProFormSelect,
  ProFormText,
  QueryFilter,
} from "@ant-design/pro-components";
import { GetUserList, DeleteUser,GetLoginTime } from "../../request/api";
import userIcon from "../../assets/img/userIcon.svg";
import moment from "moment";

export default function AdminList() {
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

  function pdDelete(e) {
    if (e == 1) {
      return "被删除状态";
    } else {
      return "活跃状态";
    }
  }

  function confirm(e) {
    //确认是否删除用户
    DeleteUser({
      id: e,
    }).then((res) => {
      message.info(res.message);
      setUpdate(update + 1);
    });
  }

  const getList = (page,pageSize,...values) => {
    //获取用户数据列表
    GetUserList({
      num: page,
      count: pageSize,
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
          <ProFormSelect
            name="userauth"
            label="用户权限"
            showSearch
            valueEnum={{
              dispatcher: "派发员",
              user: "用户",
            }}
          />
          <ProFormDateTimeRangePicker name="create" label="创建时间" colSize={3} />
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
                        <div className="create_time">
                          用户创建时间：
                          <a>
                            {moment(item.createTime)
                              .utcOffset(8)
                              .format("YYYY-MM-DD HH:mm:ss")}
                          </a>
                          
                        </div>
                      </div>
                    }
                    description={
                      <div className="title-auth">
                        <p className="title-authText">用户权限：{item.auth}</p>
                        <p className="title-active">当前用户状态：{pdDelete(item.isdelete)}</p>
                      </div>
                    }
                  />
                  <div className="pwd">
                    最近一次登陆时间: <br />
                    { "123  " }
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
