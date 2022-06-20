import {
  DrawerForm,
  ProForm,
  ProFormDatePicker,
  ProFormGroup,
  ProFormText,
  ProFormTextArea,
  ProFormSelect
} from "@ant-design/pro-components";
import { Button, message, Input } from "antd";
import React, { useEffect, useRef } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { GetUser, UpdateUser } from "../../../request/api";

export default function PopMenu(props) {
  const navigate=useNavigate();//设置跳转
  let { uid,mid,username,missionName,joinTime,finish} = props.values;
  const [description, setDescription] = useState();
  const [name, setName] = useState();
  const [prename, setPrename] = useState();
  const [jnum, setJnum] = useState();
  const [mnum, setMnum] = useState();

  function pdFinish(e) {//是否被删除
    if (e == 1) {
      return "完成";
    } else {
      return "未完成";
    }
  }

  const getUserData = () => {
    //获取用户信息
    // GetUser({
    //   id,
    //   auth,
    // }).then((res) => {
    //   if (auth === "user") {
    //     //不同用户有不同信息
    //     let { description, joinNum, username } = res.data;
    //     setName(username);
    //     setPrename(username);
    //     setDescription(description);
    //     setJnum(joinNum);
    //   } else {
    //     let { description, missionNum, username } = res.data;
    //     setName(username);
    //     setDescription(description);
    //     setMnum(missionNum);
    //   }
    // });
  };

  const update = (e) => {
    // console.log(e)
    // UpdateUser({
    //   id,
    //   auth,
    //   name: e.name,
    //   prename: prename,
    //   description: e.description,
    //   isdelete:e.isdelete
    // }).then((res) => {
    //   if (res.flag === true) {
    //     message.success(res.message);
    //     props.setUp(1);//调用父组件方法来实时更新
    //   } else {
    //     message.error(res.message);
    //   }
    // });
  };

  const waitTime = (time = 100) => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(true);
      }, time);
    });
  };

  const formRef = useRef();

  function chooseMenu() {
      return dispatcherMenu;
  }

  const dispatcherMenu = ( //派发员用的菜单
    <>
      <ProForm.Group>
        <ProFormText
          name="username"
          width="md"
          label="用户名称"
          tooltip="最长为 5 位"
          placeholder={username}
          disabled
        >
        </ProFormText>
        <ProFormText
          width="md"
          name="missionName"
          label="任务名称"
          disabled
          placeholder={missionName}
        />
      </ProForm.Group>
      <ProFormGroup>
      <ProFormSelect
          request={async () => [
            {
              value: '0',
              label: '未完成'
            },{
              value: '1',
              label: '完成'
            }
          ]}
          width="md"
          name="finish"
          label="是否完成"
          placeholder={pdFinish(finish)}
        />
      </ProFormGroup>
    </>
  );


  return (
    <DrawerForm
      title="修改"
      formRef={formRef}
      trigger={
        <Button onClick={() => getUserData()} type="primary">
          修改
        </Button>
      }
      autoFocusFirstInput
      drawerProps={{
        destroyOnClose: true,
      }}
      submitTimeout={1000}
      onFinish={async (e) => {
        await waitTime(1000);
        update(e);
        // 不返回不会关闭弹框
        return true;
      }}
    >
      {chooseMenu()}
      {/*根据权限选择用户菜单 */}
    </DrawerForm>
  );
}
