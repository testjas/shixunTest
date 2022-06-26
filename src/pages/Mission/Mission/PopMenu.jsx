import {
  DrawerForm,
  ProForm,
  ProFormDateTimeRangePicker,
  ProFormText,
  ProFormTextArea,
  ProFormSelect,
  ProFormDigit
} from "@ant-design/pro-components";
import { Button, message, Input } from "antd";
import React, { useEffect, useRef } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {UpdateMission } from "../../../request/api";

export default function PopMenu(props) {
  const navigate=useNavigate();//设置跳转
  let { mid, belong, createTime,description,endTime,finishStatus,joinedNum,missionName,missionType,startTime,status,timeStatus,userNum,isdelete } = props.values;

  function pdStatus(){
    if(isdelete===0){
      return "正常";
    }else{
      return "中止";
    }
  }
  
  const waitTime = (time = 100) => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(true);
      }, time);
    });
  };

  const formRef = useRef();


  return (
    <DrawerForm
      title="修改"
      formRef={formRef}
      trigger={
        <Button size="small" type="primary">
          查看
        </Button>
      }
      autoFocusFirstInput
      drawerProps={{
        destroyOnClose: true,
      }}
      submitTimeout={100}
      onFinish={async (e) => {
        await waitTime(100);
        // 不返回不会关闭弹框
        return true;
      }}
    >
      <ProForm.Group>
        <ProFormText
          width="md"
          name="mname"
          label="任务名称"
          tooltip="最长为20位"
          placeholder={"请输入任务名称"}
          value={missionName}
          disabled
        />

        <ProFormText
          width="md"
          name="belong"
          label="归属"
          placeholder="请输派发员名称"
          value={belong}
          disabled
        />
      </ProForm.Group>

      <ProForm.Group>
        <ProFormSelect
          disabled
          width="md"
          name="missionType"
          label="任务类型"
          value={missionType}
        />
        <ProFormDigit
          width="md"
          name="unum"
          label="可参与人数"
          placeholder="请输入人数"
          value={userNum}
          disabled
        />
      </ProForm.Group>

      <ProForm.Group>
        <ProFormSelect
          request={async () => [
            {
              value: "正常",
              label: "正常",
            },
            {
              value: "中止",
              label: "中止",
            },
          ]}
          width="md"
          name="missionStatus"
          label="任务状态"
          value={pdStatus()}
          disabled
        />
      </ProForm.Group>
      <ProFormTextArea
          name="description"
          width="md"
          label="任务描述"
          tooltip="最少输入一位，最多255个字"
          value={description}
          disabled
        />
    </DrawerForm>
  );
}
