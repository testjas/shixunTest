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
  let { id, auth, createTime,isdelete } = props.values;
  const [description, setDescription] = useState();
  const [name, setName] = useState();
  const [prename, setPrename] = useState();
  const [jnum, setJnum] = useState();
  const [mnum, setMnum] = useState();

  function pdDelete() {//是否被删除
    if (isdelete == 1) {
      return "被删除状态";
    } else {
      return "活跃状态";
    }
  }

  const getUserData = () => {
    //获取用户信息
    GetUser({
      id,
      auth,
    }).then((res) => {
      if (auth === "user") {
        //不同用户有不同信息
        let { description, joinNum, username } = res.data;
        setName(username);
        setPrename(username);
        setDescription(description);
        setJnum(joinNum);
      } else {
        let { description, missionNum, username } = res.data;
        setName(username);
        setDescription(description);
        setMnum(missionNum);
      }
    });
  };

  const update = (e) => {
    console.log(e)
    UpdateUser({
      id,
      auth,
      name: e.name,
      prename: prename,
      description: e.description,
      isdelete:e.isdelete
    }).then((res) => {
      if (res.flag === true) {
        message.success(res.message);
        props.setUp(1);//调用父组件方法来实时更新
      } else {
        message.error(res.message);
      }
    });
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
    if (auth === "user") {
      return userMenu;
    } else {
      return dispatcherMenu;
    }
  }

  const dispatcherMenu = ( //派发员用的菜单
    <>
      <ProForm.Group>
        <ProFormText
          name="name"
          width="md"
          label="用户名称"
          tooltip="最长为 5 位"
          placeholder={name}
        >
          <Input maxLength={5} placeholder={name}></Input>
        </ProFormText>
        <ProFormText
          width="md"
          name="auth"
          label="用户权限"
          disabled
          placeholder={auth}
        />
      </ProForm.Group>
      <ProFormGroup>
      <ProFormSelect
          request={async () => [
            {
              value: 'delete',
              label: '已删除'
            },{
              value: 'active',
              label: '未删除'
            }
          ]}
          width="md"
          name="isdelete"
          label="用户状态"
          placeholder={pdDelete()}
        />
        <ProFormTextArea
          name="description"
          width="md"
          label="用户描述"
          value={description}
          placeholder={description}
        />
      </ProFormGroup>
      <ProForm.Group>
        <ProFormText
          name="jnum"
          width="md"
          label="用户发布的任务数"
          disabled
          placeholder={mnum}
        />
        <ProFormDatePicker
          disabled
          width="md"
          name="createtime"
          label="用户创建时间"
          placeholder={createTime}
        />
      </ProForm.Group>
    </>
  );

  const userMenu = ( //用户用的菜单
    <>
      <ProForm.Group>
        <ProFormText
          name="name"
          width="md"
          label="用户名称"
          tooltip="最长为 5 位"
        >
          <Input maxLength={5} placeholder={name}></Input>
        </ProFormText>
        <ProFormText
          width="md"
          name="auth"
          label="用户权限"
          disabled
          placeholder={auth}
        />
      </ProForm.Group>
      <ProFormGroup>
      <ProFormSelect
          request={async () => [
            {
              value: 'delete',
              label: '已删除'
            },{
              value: 'active',
              label: '未删除'
            }
          ]}
          width="md"
          name="isdelete"
          label="用户状态"
          placeholder={pdDelete()}
        />
        <ProFormTextArea
          name="description"
          width="md"
          label="用户描述"
          value={description}
          placeholder={description}
        />
      </ProFormGroup>
      <ProForm.Group>
        <ProFormText
          name="jnum"
          width="md"
          label="用户参加的任务数"
          disabled
          placeholder={jnum}
        />
        <ProFormDatePicker
          disabled
          width="md"
          name="createtime"
          label="用户创建时间"
          placeholder={createTime}
        />
      </ProForm.Group>
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
