import {
  ModalForm,
  ProFormText,
  ProFormSelect,
} from "@ant-design/pro-components";
import { Button, message } from "antd";
import React from "react";
import { AddUserMission } from "../../../request/api";
import { PlusOutlined } from '@ant-design/icons';

export default function AddUser(props) {
  const waitTime = (time = 100) => {//设置等待时间
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(true);
      }, time);
    });
  };

  const addUser = (values) => {
    AddUserMission({
      username: values.username, //传递注册用户信息
      missionName: values.missionName,
      disName:localStorage.getItem("username")
    }).then((res) => {
      if (res.flag === true) {
        message.success(res.message); //登陆成功后的提示
        props.setUp(1);//插入用户后实时更新父组件
      } else {
        message.error(res.message);
      }
    });
  };

  return (
    <ModalForm
      title="添加用户"
      trigger={<Button type="primary"><PlusOutlined/>添加新用户</Button>}
      submitter={{
        searchConfig: {
          submitText: "确认",
          resetText: "取消",
        },
      }}
      onFinish={async (values) => {
        await waitTime(1500);
        addUser(values);
        message.success("提交成功");
        return true;
      }}
    >
      <ProFormText
        width="md"
        name="username"
        label="用户名称"
        tooltip="最长为5位"
        placeholder="请输入名称"
        
        rules={[{
            required:true,
            max:5
        }]}
      />

<ProFormText
        width="md"
        name="missionName"
        label="任务名称"
        placeholder="请输入任务"
        rules={[{
            required:true
        }]}
      />
    </ModalForm>
  );
}
