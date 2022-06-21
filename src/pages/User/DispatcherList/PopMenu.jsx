import {
  DrawerForm,
  ProForm,
  ProFormGroup,
  ProFormText,
  ProFormSelect
} from "@ant-design/pro-components";
import { Button, message, Input } from "antd";
import React, { useRef } from "react";
import { useState } from "react";
import { UpdateUserFinish } from "../../../request/api";

export default function PopMenu(props) {
  let { uid,mid,username,missionName,joinTime,finish} = props.values;

  function pdFinish(e) {//是否被删除
    if (e == 1) {
      return "完成";
    } else {
      return "未完成";
    }
  }

  const update = (e) => {
    UpdateUserFinish({
      mid,
      uid,
      finish:e.finish
    }).then((res)=>{
      if(res.flag===true){
        message.success(res.message);
      }else{
        message.error(res.message);
      }
      props.setUp(1);
    })
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
        <Button type="primary">
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
